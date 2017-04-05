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
@interface AKTransferCommand : AKCardCommand

@property (nonatomic, strong) AKCommandAPDU * apdu;

@property (nonatomic, strong) AKResponseAPDU * response;

+(instancetype)transferAPDU:(AKCommandAPDU*)apdu;

@end




@interface AKNFCTransferCommand : AKTransferCommand

@end