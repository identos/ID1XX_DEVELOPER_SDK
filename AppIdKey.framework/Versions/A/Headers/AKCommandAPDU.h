//
//  AKCommandAPDU.h
//  AppIdDemo
//
//  Created by Alec Laws on 2014-05-16.
//  Copyright (c) 2014 Spoke Technologies. All rights reserved.
//

#import <Foundation/Foundation.h>
typedef NS_ENUM(NSUInteger, AKTransferType) {
    AKTransferTypeNoData = 0, // no data sent or received (no lc, data, le )
    AKTransferTypeReceiveData, // data received (no lc)
    AKTransferTypeSendData, // data sent (no le)
    AKTransferTypeSendReceiveData, // data sent & received
};

@interface AKCommandAPDU : NSObject
@property (nonatomic) unsigned char cls; // class
@property (nonatomic) unsigned char ins; // instruction
@property (nonatomic) unsigned char p1; // parameter 1
@property (nonatomic) unsigned char p2; // parameter 2


@property (nonatomic) unsigned char Lc; // length of data
@property (nonatomic, strong) NSData * data; // data sent
@property (nonatomic) unsigned char Le; // maximum number of bytes expected in resposne

@property (nonatomic) AKTransferType type;


+ (instancetype)withCls:(unsigned char)aCls
                    ins:(unsigned char)anIns
                     p1:(unsigned char)aP1
                     p2:(unsigned char)aP2
                   data:(NSData*)aData
                     Le:(unsigned char)aLe;


@end