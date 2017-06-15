//
//  AKMessageViewController.m
//  Demo App
//
//  Created by istvan czobel on 18.09.14.
//  Copyright (c) 2014 appidkey gmbh. All rights reserved.
//

#import "AKMessageViewController.h"


@interface AKMessageViewController ()

@property (weak, nonatomic) IBOutlet UILabel *message1;
@property (weak, nonatomic) IBOutlet UILabel *message2;
@property (weak, nonatomic) IBOutlet UILabel *message3;

@end

@implementation AKMessageViewController

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

    // Clear befor rewrite
    self.message1.text = @"";
    self.message2.text = @"";
    self.message3.text = @"";

    self.message1.text = self.message;
    self.message1.textColor = [UIColor colorWithRed:220.0f/255.0f green:5.0f/255.0f blue:5.0f/255.0f alpha:1.0f];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
