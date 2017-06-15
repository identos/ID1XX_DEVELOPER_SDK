//
//  AKDeviceInfoViewController.m
//  Demo App
//
//  Created by Ronja Lars Wilkening on 10.03.14.
//  Copyright (c) 2014 appidkey gmbh. All rights reserved.
//

#import "AKDeviceInfoViewController.h"

#import <AppIdKey/AppIdKey.h>

#import <ExternalAccessory/ExternalAccessory.h>

#import "AKMessageViewController.h"
#import "AKBankCardInfoViewController.h"
#import "AKHealthCardInfoViewController.h"

@interface AKDeviceInfoViewController ()
@property (nonatomic, strong) IBOutlet UIButton * readCardButton;
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;
@property (weak, nonatomic) IBOutlet UILabel *firmwareLabel;
@property (weak, nonatomic) IBOutlet UILabel *hardwareLabel;
@property (weak, nonatomic) IBOutlet UILabel *serialLabel;
@property (weak, nonatomic) IBOutlet UILabel *nameLabel;
@property (weak, nonatomic) IBOutlet UILabel *messageLabel;
@property (nonatomic, strong) IBOutlet UIActivityIndicatorView* activityIndicator;
- (IBAction)readCard:(id)sender;

@end

@implementation AKDeviceInfoViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.hidesBackButton = YES;
    monitoringSended = NO;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    AKDeviceInfo * deviceInfo = [[AppIdKeyController sharedInstance] deviceInfo];
    
    self.titleLabel.text = @"Accessory Info";
    self.nameLabel.text = deviceInfo.name;
    self.firmwareLabel.text = deviceInfo.firmwareRevision;
    self.hardwareLabel.text = deviceInfo.hardwareRevision;
    self.serialLabel.text = deviceInfo.serialNumber;
    
    // Start polling for card
    [[AppIdKeyController sharedInstance] startPolling:^(AKCard* card, NSError * error) {
        if(card != nil){
            [self.readCardButton setEnabled:YES];
        }
        else {
            [self.readCardButton setEnabled:NO];
        }
    }];

}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
    [[AppIdKeyController sharedInstance] stopPolling];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Navigation

- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{

    if ([segue.identifier isEqualToString:@"showCardInfo"])
    {
  
        AKBankCard * creditCard = [[AppIdKeyController sharedInstance] readCard];
        
        if(creditCard.strIBAN != nil)
        {
            //creditCard.strIBAN = [self CalculateIBAN:creditCard];
        }
        
        AKBankCardInfoViewController* cvc = segue.destinationViewController;
        cvc.creditCard = creditCard;
    }
    else if([segue.identifier isEqualToString:@"showHCardInfo"])
    {
        AKHealthCard * healthCard = [[AppIdKeyController sharedInstance] readCard];
        
        AKHealthCardInfoViewController* hvc = segue.destinationViewController;
        hvc.healthCard = healthCard;
    }
    else if([segue.identifier isEqualToString:@"showMessage"])
    {
        
        AKMessageViewController* mvc = segue.destinationViewController;
        mvc.message = @"card not inserted";
    }

}

- (IBAction)readCard:(id)sender
{
    
    // Card must be availbile to read
    if ([[AppIdKeyController sharedInstance] isCardAvailable]) {
        
        [[UIApplication sharedApplication] setNetworkActivityIndicatorVisible:YES];
        
        AKCardCommand * atr = [AKAnswerToResetCommand command];
        NSData * atrData = [[AppIdKeyController sharedInstance] performCommand:atr];
        
        ISLog(@"ATR %@: %@", (atr.status == AKTerminalCommandSuccess)?@"y":@"n", atrData);

        AKCardType cardtype = [[AppIdKeyController sharedInstance] cardType];
        
        // Display Card by type
        switch (cardtype) {
            case AKCardTypeHealth:
                [[AppIdKeyController sharedInstance] stopPolling];
                [self performSegueWithIdentifier:@"showHCardInfo" sender:self];
                break;
            case AKCardTypeCredit:
                [[AppIdKeyController sharedInstance] stopPolling];
                [self performSegueWithIdentifier:@"showCardInfo" sender:self];
                break;
            default:
                [[AppIdKeyController sharedInstance] stopPolling];
                [self performSegueWithIdentifier:@"showMessage" sender:self];
                break;
        }
    }
    else {
        [[AppIdKeyController sharedInstance] stopPolling];
        [self performSegueWithIdentifier:@"showMessage" sender:self];
    }

}



@end
