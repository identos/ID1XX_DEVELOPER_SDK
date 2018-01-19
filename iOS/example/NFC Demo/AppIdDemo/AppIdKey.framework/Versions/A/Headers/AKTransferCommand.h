//
//  AKTransferCommand.h
//  AppIdKey
//
//  Created by Alec Laws on 2015-06-23.
//  Copyright (c) 2015 Identos. All rights reserved.
//

#import "AKCardCommand.h"



#import "AKCommandAPDU.h"
#import "AKResponseAPDU.h"


/**
 */
@interface AKTransferCommand : AKCardCommand

/**
 @name Properties
 */

/**
 APDU Command
 */
@property (nonatomic, strong) AKCommandAPDU * apdu;


/**
 APDU Response
 */
@property (nonatomic, strong) AKResponseAPDU * response;

/**
 @name Methods
 */

/**
 Transfer AKCommandAPDU

 @param apdu AKCommandAPDU
 @return The AKTransferCommand
 */
+(instancetype)transferAPDU:(AKCommandAPDU*)apdu;

@end


/**
 */
@interface AKNFCTransferCommand : AKTransferCommand


-(NSData*)processResponseData:(NSData *)resposne;

@end


@interface AKNFCRawTransferCommand : AKCardCommand

@property (nonatomic, strong) NSData * input;
@property (nonatomic, strong) NSData * output;

+(instancetype)withData:(NSData*)data;

@end

