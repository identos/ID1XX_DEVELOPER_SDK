//
//  AKMasterViewController.m
//  Demo App
//
//  Created by Ronja Lars Wilkening on 10.03.14.
//  Copyright (c) 2014 appidkey gmbh. All rights reserved.
//

#import "AKRootViewController.h"

#import <AppIdKey/AppIdKeyController.h>

#import <ExternalAccessory/ExternalAccessory.h>
#import <AppIdKey/ISLog.h>
//#import "AKDevInfoController.h"

@interface AKRootViewController () {
    NSMutableArray *_objects;
}

@end

@implementation AKRootViewController


- (BOOL)canBecomeFirstResponder
{
    return YES;
}
- (void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent *)event
{
    if (motion == UIEventSubtypeMotionShake)
    {
        UIViewController * vc = [[UIViewController alloc] init];
        vc.view.backgroundColor = [UIColor lightGrayColor];
        
        CGRect f = self.view.bounds;
        f.origin.y += 50;
        f.size.height -= f.origin.y;
        UITextView * tv = [[UITextView alloc] initWithFrame:f];
        tv.userInteractionEnabled = YES;
        tv.text = [[ISLog sharedLog] logText];
        
        [vc.view addSubview:tv];
        
        UITapGestureRecognizer * tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(dismissViewController:)];
        tap.numberOfTapsRequired =2;
        [vc.view addGestureRecognizer:tap];
        
        [self presentViewController:vc animated:YES completion:nil];
        
    } 
}

-(IBAction)dismissViewController:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}


- (void)awakeFromNib
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        self.preferredContentSize = CGSizeMake(320.0, 600.0);
    }
    [super awakeFromNib];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void) viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];

    // setOnDeviceConnected Block
    [[AppIdKeyController sharedInstance] setOnDeviceConnected:^{
        if ([[AppIdKeyController sharedInstance] isDeviceConnected]) {
            ISLog(@"found terminal");
            [self performSegueWithIdentifier:@"cardReaderFound" sender:nil];
        }
    }];
    
    // setOnDeviceDisconnected Block
    [[AppIdKeyController sharedInstance] setOnDeviceDisconnected:^{
          [self.navigationController popToRootViewControllerAnimated:YES];
    }];
 
    // Check for device
    if ([[AppIdKeyController sharedInstance] isDeviceConnected]) {
        ISLog(@"found terminal");
        [self performSegueWithIdentifier:@"cardReaderFound" sender:nil];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}




@end
