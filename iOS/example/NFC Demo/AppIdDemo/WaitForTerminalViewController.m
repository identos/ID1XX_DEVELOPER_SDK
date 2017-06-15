//
//  ViewController.m
//  AppIdDemo
//
//  Created by Alec Laws on 2016-01-18.
//  Copyright © 2016 identos. All rights reserved.
//

#import "WaitForTerminalViewController.h"

#import <AppIdKey/AppIdKey.h>

#import "NFCCardViewController.h"



@interface WaitForTerminalViewController ()

@end

@implementation WaitForTerminalViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
        
    [self.navigationController setNavigationBarHidden:YES];
    
    [[AppIdKeyController sharedInstance] setOnDeviceConnected:^{
        
        // why do i have to check this here????
        if ([[AppIdKeyController sharedInstance] isDeviceConnected]) {
            
            [self terminalConnectedAction];
        }
    }];
    
    [[AppIdKeyController sharedInstance] setOnDeviceDisconnected:^{
        [self.navigationController popToRootViewControllerAnimated:YES];
    }];
    
    
    if ([[AppIdKeyController sharedInstance] isDeviceConnected]) {

        [self terminalConnectedAction];

    }
}


-(void)terminalConnectedAction {
    
    if ([self.navigationController.viewControllers count] == 1) {
        
        NFCCardViewController * c = [[NFCCardViewController alloc] init];
        [self.navigationController pushViewController:c animated:YES];
    }
}



- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
