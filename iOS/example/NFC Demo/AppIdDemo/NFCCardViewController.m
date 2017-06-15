//
//  NFCCardViewController.m
//  AppIdDemo
//
//  Created by Alec Laws on 2016-01-18.
//  Copyright © 2016 identos. All rights reserved.
//

#import "NFCCardViewController.h"


#import <AppIdKey/AppIdKey.h>


#import <AppIdKey/ISLog.h>


@class NFCController;
@interface NFCCardViewController ()

@property (nonatomic, strong) NFCController * state;

@property (nonatomic, strong) IBOutlet UIImageView * statusImageView;
@property (nonatomic, strong) IBOutlet UILabel * statusLabel;

@property (nonatomic, strong) IBOutlet UIButton * discoverButton;
@property (nonatomic, strong) IBOutlet UIButton * clearButton;




@property (nonatomic, strong) IBOutlet UITextView * resultsView;
@property (nonatomic, strong) NSObject * logObserver;
@end




enum {
    NFCStateNONE,
    NFCStateDISCOVERING ,
    NFCStateFOUND,
};
typedef NSUInteger NFCState;


@interface NFCController : NSObject

+(NFCState)state;
-(NFCState)state;


-(void)start:(NFCCardViewController*)vc;
-(void)end:(NFCCardViewController*)vc;
@end

@implementation NFCController

+(NFCState)state { NSAssert(NO, @"NFCCardState: +state: ABSTRACT INVOKE"); return 0; }
-(NFCState)state { return [[self class] state]; }


-(void)start:(NFCCardViewController*)vc {}
-(void)end:(NFCCardViewController*)vc {}
@end

@interface NFCNONE : NFCController

@end
@interface NFCDISCOVERING : NFCController

@end
@interface NFCFOUND : NFCController
@property (nonatomic, strong) id<AKNFCCard> card ;
@property (nonatomic, strong) NSError * error;
@end




@implementation NFCNONE

+(NFCState)state { return NFCStateNONE; }

-(void)start:(NFCCardViewController*)vc {
    
    vc.statusLabel.text = NSLocalizedString(@"No Patient", @"");
    vc.statusImageView.image = [UIImage imageNamed:@"patient-none"];
    vc.discoverButton.enabled = YES;
    vc.clearButton.hidden = YES;
}

@end


@implementation NFCDISCOVERING
+(NFCState)state { return NFCStateDISCOVERING; }

-(void)start:(NFCCardViewController*)vc {

//    [self.discoverButton setTitle:NSLocalizedString(@"Stop Discovery", @"") forState:UIControlStateNormal];
//    self.nfcStatusLabel.text = NSLocalizedString(@"Discovering...", @"");
    
    vc.statusLabel.text = NSLocalizedString(@"Scanning for Patient", @"");
    vc.statusImageView.image = [UIImage imageNamed:@"patient-scanning"];
    vc.discoverButton.enabled = NO;
    vc.discoverButton.alpha = 0.5;

    vc.clearButton.hidden = YES;
    
    // let ui update then start!
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        
        ISLog(@"starting Discovery");
        
        [[AppIdKeyController sharedInstance] startDiscovery:^(id<AKNFCCard> card, NSError *error) {

            NFCFOUND * found = [NFCFOUND new];
            found.card = card;
            found.error = error;
            vc.state = found;
            
            ISLog(@"startDiscovery(%@, %@)", card, error);
            
            
            
          }];
        
    });
}

-(void)end:(NFCCardViewController*)vc {
    
    vc.discoverButton.alpha = 1.0;
    
    [[AppIdKeyController sharedInstance] stopDiscovery];
}


@end


@implementation NFCFOUND

+(NFCState)state { return NFCStateFOUND; }

-(void)start:(NFCCardViewController *)vc {
    
    vc.statusLabel.text = NSLocalizedString(@"Patient Found", @"");
    vc.statusImageView.image = [UIImage imageNamed:@"patient-found"];
    vc.discoverButton.enabled = YES;
    vc.clearButton.hidden = NO;
    
    if (self.error) {
        
        ISLog(@"Card Not Found: %lu", (unsigned long)self.error.code);
        return ;
    }
    
    
    if(self.card) {
        
        id<AKNFCCard> card = self.card;
        
        NSData * data;
        AKNFCTransferCommand * transfer;
        AKCommandAPDU * apdu;
        
        
        apdu = [AKCommandAPDU withCls:0xFF ins:0xCA p1:0x00 p2:0x00 data:0x00 Le:0x0];
        apdu.type = AKTransferTypeReceiveData;
        
        transfer = [AKNFCTransferCommand transferAPDU:apdu];
        data = [card performCommand:transfer];
        ISLog(@"FFCA0000: %@", data);
        
        
        
        NSData * paysys = [@"1PAY.SYS.DDF01" dataUsingEncoding:NSASCIIStringEncoding];
        apdu = [AKCommandAPDU withCls:0x0 ins:0xA4 p1:0x04 p2:0x0 data:paysys Le:0x0];
        apdu.type = AKTransferTypeSendReceiveData;
        
        transfer = [AKNFCTransferCommand transferAPDU:apdu];
        data = [card performCommand:transfer];
        ISLog(@"NFC 1PAY.SYS.DDF01: %@", data);
        
        
        
        NSData * paysys2 = [@"2PAY.SYS.DDF01" dataUsingEncoding:NSASCIIStringEncoding];
        apdu = [AKCommandAPDU withCls:0x0 ins:0xA4 p1:0x04 p2:0x0 data:paysys2 Le:0x0];
        apdu.type = AKTransferTypeSendReceiveData;
        
        transfer = [AKNFCTransferCommand transferAPDU:apdu];
        data = [card performCommand:transfer];
        ISLog(@"NFC 2PAY.SYS.DDF01: %@", data);

        
    }

}


@end



@implementation NFCCardViewController


#pragma mark - View Lifecycle

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
 
    self.discoverButton.layer.cornerRadius = 5.0;
    
    [self setupInitialState];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    

    [self observeLog];
    
    self.resultsView.text = [[ISLog sharedLog] logText];
}

-(void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    

    [[AppIdKeyController sharedInstance] stopDiscovery];
    
    [self unobserveLog];
}

#pragma mark - Log

-(void)observeLog {
    
    self.logObserver = [[NSNotificationCenter defaultCenter] addObserverForName:kNotificationLogChanged object:nil queue:nil usingBlock:^(NSNotification * _Nonnull note) {
       
        self.resultsView.text = [[ISLog sharedLog] logText];
        

        [self scrollTextViewToBottom:self.resultsView];
        
    }];
}

-(void)scrollTextViewToBottom:(UITextView *)textView {
    if(textView.text.length > 0 ) {
        NSRange bottom = NSMakeRange(textView.text.length -1, 1);
        [textView scrollRangeToVisible:bottom];
    }
    
}

-(void)unobserveLog {
    
    [[NSNotificationCenter defaultCenter] removeObserver:self.logObserver];
    self.logObserver = nil;
    
}

-(IBAction)clearLog:(id)sender {
    [[ISLog sharedLog] clear];
}

#pragma mark - NFC State


-(void)setupInitialState {

    self.state = [NFCNONE new];
    
}
-(void)setState:(NFCController *)state {
    if (state != _state) {
        [_state end:self];
        _state = state;
        [_state start:self];
    }
}

#pragma mark - Discover


-(IBAction)discoverButtonAction:(id)sender {
    
    ISLog(@"discoverButtonAction");
    
    if ([self.state state] != NFCStateDISCOVERING) {
        self.state = [NFCDISCOVERING new];
    }
    

}

-(IBAction)clearAction:(id)sender {
    
    [self setupInitialState];
}

@end
