//
//  AppIdKeyController.h
//  AppIdKey
//
//  Created by Alec Laws on 2014-11-24.
//  Copyright (c) 2014 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "AKCard.h"
#import "AKNFCCard.h"
#import "AKDeviceInfo.h"
#import "AKCardCommand.h"


@class AKNFCTransferCommand;



/**
 AKPollingState
 
 - AKPollingStateOFF: Polling is Off
 - AKPollingStateON: Polling is On
 - AKPollingStateDISCOVER: Discover State
 - AKPollingStatePRESENT: Card Present State
 */
typedef  NS_ENUM(NSUInteger, AKPollingState) {
    AKPollingStateOFF = 1,
    AKPollingStateON,
    AKPollingStateDISCOVER,
    AKPollingStatePRESENT
};

/**
 This class manages the low level communication with the appidkey hardware.
 Supported External accessory protocol is "com.scapiscreader.iOS.a.a.a"
 please insert it in your project.plist
 */
@interface AppIdKeyController : NSObject

/**
 @name Properties
 */

/**
 The AKPollingState
 */
@property (nonatomic) AKPollingState state;

/**
 Is the device connected
 */
@property (nonatomic, readonly, getter=isDeviceConnected) BOOL deviceConnected;

/**
 Block for when device connects
 */
@property (nonatomic, copy) dispatch_block_t onDeviceConnected;

/**
 Block for when device disconnects
 */
@property (nonatomic, copy) dispatch_block_t onDeviceDisconnected;

/**
 Is there a card currently availible in the reader
 */
@property (nonatomic, readonly, getter=isCardAvailable) BOOL cardAvailable;

/**
 @name Methods
 */

/**
 The shared instance of the controller

 @return AppIdKeyController
 */
+(instancetype)sharedInstance;

/**
 Get the info of the currently connceted device

 @return AKDeviceInfo deviceInfo
 */
-(AKDeviceInfo *)deviceInfo;

/**
 * This method establish the Card Type. Software support responding serial numbers
 * @return returns the Card Type "H" for health card "B" for Bank Card "E" for not valid card
 */
-(AKCardType)cardType;

/**
 * This method reads the card depending on the type
 */
-(id)readCard;


/**
 * Performs the given command if the card is inserted and not busy
 *
 * @param command The command to perform
 *
 * @return The raw data returned on success
 */
-(NSData*)performCommand:(AKCardCommand*)command ;

/**
 * This method will begin polling for changes in smart cards. 
 * ResultBlock will be run when a card is either inserted or removed.
 * @param resultBlock The block that will be run
 */
-(void)startPolling:(void(^)(AKCard*, NSError*))resultBlock;

/**
 * This method will stop polling for changes in smart cards.
 */
-(void)stopPolling;


/**
 * Begin polling for NFC card, once found discovery is stopped
 * @note on discovery, polling is stopped. 
 *
 * @param resultBlock The block that will be run when card is detected
    - card Discovered card to exchange data with
    - error Error encountered during discovery
    - completion If card is found, this must to called when finished sending commands to the card
 */
-(void)startDiscovery:(void(^)(AKNFCCard * card, NSError* error, void(^completion)()))resultBlock;


/**
 * Stop polling for NFC Discovery
 */
-(void)stopDiscovery;


@end


FOUNDATION_EXPORT NSString * const AKNFCErrorDomain;
