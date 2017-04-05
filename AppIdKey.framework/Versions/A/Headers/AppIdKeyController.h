//
//  AppIdKeyController.h
//  AppIdKey
//
//  Created by Alec Laws on 2014-11-24.
//  Copyright (c) 2014 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "AKCard.h"
#import "AKDeviceInfo.h"
#import "AKCardCommand.h"


@class AKNFCTransferCommand;
@protocol AKNFCCard <NSObject>

-(NSData*)performCommand:(AKNFCTransferCommand*)command;

@end

/**
 * This class manages the low level communication with the appidkey hardware.
 * Supported External accessory protocol is "com.scapiscreader.iOS.a.a.a"
 * please insert it in your project.plist
 */
@interface AppIdKeyController : NSObject

+(instancetype)sharedInstance;

@property (nonatomic, readonly, getter=isDeviceConnected) BOOL deviceConnected;

-(AKDeviceInfo *)deviceInfo;
@property (nonatomic, copy) dispatch_block_t onDeviceConnected;
@property (nonatomic, copy) dispatch_block_t onDeviceDisconnected;


@property (nonatomic, readonly, getter=isCardAvailable) BOOL cardAvailable;
/**
 * This method establish the Card Type. Software support responding serial numbers
 * @return returns the Card Type "H" for health card "B" for Bank Card "E" for not valid card
 */
-(AKCardType)cardType;
/**
 * This method read the card dependig of type
 */
-(id)readCard;


/**
 Performs the given command if the card is inserted and not busy
 
 @param command The command to perform
 
 @return The raw data returned on success
 */
-(NSData*)performCommand:(AKCardCommand*)command ;


// NFC COMMADNS
-(void)startDiscovery:(void(^)(id<AKNFCCard>, NSError*))resultBlock;
-(void)stopDiscovery ;


@end


FOUNDATION_EXPORT NSString * const AKNFCErrorDomain;