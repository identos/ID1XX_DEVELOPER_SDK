//
//  AKNFCCard.h
//  AppIdKey
//
//  Created by Alec Laws on 2018-01-17.
//  Copyright © 2018 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "AKTransferCommand.h"

@interface AKNFCCard : NSObject

/**
 Performs AKNFCTransferCommand command
 
 @param command The AKNFCTransferCommand
 @return NSData result from card (including sw1sw2)
 */
-(NSData*)performCommand:(AKNFCTransferCommand*)command;

/**
 Transfer data directly to card
 
 @param command The AKNFCTransferCommand
 @return NSData result from card (including sw1sw2)
 
 */
-(NSData*)transferData:(NSData*)input;

@end

