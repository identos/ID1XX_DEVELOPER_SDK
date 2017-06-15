//
//  AKNFCCommand.h
//  AppIdKey
//
//  Created by Alec Laws on 2015-12-29.
//  Copyright © 2015 Identos. All rights reserved.
//

#import <AppIdKey/AppIdKey.h>

#import "AKCardCommand.h"


/**

 */
@interface AKNFCCommand : AKCardCommand

/**
 @name Methods
 */

/**
 Power on the NFC chip.
 */
+(instancetype)nfcPowerOn;

/**
 Power off the NFC chip.
 */
+(instancetype)nfcPowerOff;


/**
 Enable discovery for the NFC chip.
 */
+(instancetype)nfcStartDiscovery;


/**
 Indicate card present.
 */
+(instancetype)nfcDetectCard;


/**
 Transfer to NFC device.
 */
+(instancetype)nfcTransfer;


/**
 This method reads the card depending on the type.
 */
+(instancetype)readCard;


/**
 This method will read the card and track2Data.
  */
+(instancetype)track2Data;


/**
 Display the last cards track2 data.
 */
+(instancetype)panShort;

@end


