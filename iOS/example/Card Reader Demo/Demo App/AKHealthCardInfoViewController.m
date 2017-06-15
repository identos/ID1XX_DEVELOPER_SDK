//
//  AKHcardInfoViewController.m
//  TempApp
//
//  Created by istvan czobel on 28.09.14.
//  Copyright (c) 2014 appidkey gmbh. All rights reserved.
//

#import "AKHealthCardInfoViewController.h"


@interface AKHealthCardInfoViewController ()
@property (weak, nonatomic) IBOutlet UILabel *message;
@property (weak, nonatomic) IBOutlet UILabel *insurantLabel;
@property (weak, nonatomic) IBOutlet UILabel *birthdayLabel;
@property (weak, nonatomic) IBOutlet UILabel *firstnameLabel;
@property (weak, nonatomic) IBOutlet UILabel *surnameLabel;

@property (weak, nonatomic) IBOutlet UILabel *zipCodeLabel;
@property (weak, nonatomic) IBOutlet UILabel *cityLabel;
@property (weak, nonatomic) IBOutlet UILabel *streetLabel;
@property (weak, nonatomic) IBOutlet UILabel *houseNumberLabel;

@property (weak, nonatomic) IBOutlet UILabel *serialnumberLabel;

@property (weak, nonatomic) IBOutlet UILabel *insuranceNameLabel;
@property (weak, nonatomic) IBOutlet UILabel *IKNumberLabel;
@property (weak, nonatomic) IBOutlet UILabel *IKNumber2Label;
@property (weak, nonatomic) IBOutlet UILabel *insuranceBeginLabel;
@property (weak, nonatomic) IBOutlet UILabel *insuranceEndLabel;
@property (weak, nonatomic) IBOutlet UILabel *insurantStatusLabel;
@property (weak, nonatomic) IBOutlet UILabel *insurantArtLabel;
@property (weak, nonatomic) IBOutlet UILabel *isAmbulantLabel;
@property (weak, nonatomic) IBOutlet UILabel *isStationeryLabel;
- (IBAction)readCert:(id)sender;


@end

@implementation AKHealthCardInfoViewController

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
    
    if (self.healthCard) {
        self.insurantLabel.text         = self.healthCard.insurantID;
        self.firstnameLabel.text        = self.healthCard.firstname;
        self.surnameLabel.text          = self.healthCard.lastname;
        self.birthdayLabel.text         = self.healthCard.birthday;
        self.zipCodeLabel.text          = [NSString stringWithFormat:@"%li", (long)self.healthCard.zipCode];
        self.cityLabel.text             = self.healthCard.city;
        self.streetLabel.text           = self.healthCard.street;
        self.houseNumberLabel.text      = self.healthCard.houseNumber;
        self.serialnumberLabel.text     = self.healthCard.serialnumber;
        self.IKNumberLabel.text         = self.healthCard.IKNumber;
        self.insuranceBeginLabel.text   = self.healthCard.insuranceBegin;
        self.insuranceEndLabel.text     = self.healthCard.insuranceEnd;
        self.insuranceNameLabel.text    = self.healthCard.insuranceName;
        self.insurantStatusLabel.text   = self.healthCard.insurantStatus;
        self.insurantArtLabel.text      = self.healthCard.insurantArt;

#ifdef DEBUG
        ISLog(@"Titel: %@", self.healthCard.titel);
        ISLog(@"Namenszusatz: %@", self.healthCard.namenszusatz);
        ISLog(@"Vorsatzwort: %@", self.healthCard.vorsatzwort);
        
        ISLog(@"Vorname: %@", self.healthCard.firstname);
        ISLog(@"Nachname: %@", self.healthCard.lastname);
        ISLog(@"Geburtstag: %@", self.healthCard.birthday);
        ISLog(@"PLZ: %@", [NSString stringWithFormat:@"%li", (long)self.healthCard.zipCode]);
        ISLog(@"Ort: %@", self.healthCard.city);
        ISLog(@"Strasse: %@", self.healthCard.street);
        ISLog(@"Hausnummer: %@", self.healthCard.houseNumber);
        
        ISLog(@"VesichertenNr: %@", self.healthCard.insurantID);
        ISLog(@"Begin: %@", self.healthCard.insuranceBegin);
        ISLog(@"End: %@", self.healthCard.insuranceEnd);
        ISLog(@"IKNummer: %@", self.healthCard.IKNumber);
        ISLog(@"IKNummer2: %@", self.healthCard.IKNumber2);
        ISLog(@"Krankenkasse: %@", self.healthCard.insuranceName);
        
        ISLog(@"Status: %@", self.healthCard.insurantStatus);
        ISLog(@"Art: %@", self.healthCard.insurantArt);
        
        ISLog(@"AerztlicheVersorgung: %@", self.healthCard.AerztlicheVersorgung);
        ISLog(@"ZahnaerztlicheVersorgung: %@", self.healthCard.ZahnaerztlicheVersorgung);
        ISLog(@"VeranlassteLeistungen: %@", self.healthCard.VeranlassteLeistungen);
        ISLog(@"WOP: %@", self.healthCard.WOP);
        
        ISLog(@"personalInsuranceXML: %@", self.healthCard.personalInsuranceXML);
        ISLog(@"generalInsuranceDataXML: %@", self.healthCard.generalInsuranceDataXML);
#endif
   
    }
}

- (IBAction)readCert:(id)sender
{
    // Display Certificate data (or length)
    NSData* Certificate = [self.healthCard readCertificate];
    NSString* response = [[NSString alloc] initWithData:Certificate encoding:NSASCIIStringEncoding];
    if(Certificate.length > 100)
        self.message.text = [NSString stringWithFormat:@"Certlength =%lu", (unsigned long)Certificate.length];
    else
        self.message.text = response;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
