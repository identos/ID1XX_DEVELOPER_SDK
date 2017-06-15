//
//  AKCardInfoViewController.m
//  Demo App
//
//  Created by Ronja Lars Wilkening on 11.03.14.
//  Copyright (c) 2014 appidkey gmbh. All rights reserved.
//

#import "AKBankCardInfoViewController.h"


@interface AKBankCardInfoViewController ()
@property (weak, nonatomic) IBOutlet UILabel *message;
@property (weak, nonatomic) IBOutlet UILabel *accountLabel;
@property (weak, nonatomic) IBOutlet UILabel *cardHolderLabel;
@property (weak, nonatomic) IBOutlet UILabel *applicationLabel;
@property (weak, nonatomic) IBOutlet UILabel *expirationLabel;
@property (weak, nonatomic) IBOutlet UILabel *countryLabel;
@property (weak, nonatomic) IBOutlet UILabel *sIBANLabel;
@property (weak, nonatomic) IBOutlet UILabel *sTrack2Data;


@end

@implementation AKBankCardInfoViewController

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
    
    if (self.creditCard) {
        self.accountLabel.text      = self.creditCard.accountNumber;
        self.cardHolderLabel.text   = self.creditCard.name;
        self.applicationLabel.text  = self.creditCard.application;
        self.expirationLabel.text   = [NSString stringWithFormat:@"%@/20%@", self.creditCard.expirationMonth, self.creditCard.expirationYear];
        self.countryLabel.text      = self.creditCard.countryCode;
        self.sTrack2Data.text       = self.creditCard.track2Data;
    }
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
