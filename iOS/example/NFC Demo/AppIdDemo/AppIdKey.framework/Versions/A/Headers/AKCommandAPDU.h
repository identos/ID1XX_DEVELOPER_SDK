//
//  AKCommandAPDU.h
//  AppIdDemo
//
//  Created by Alec Laws on 2014-05-16.
//  Copyright (c) 2014 Spoke Technologies. All rights reserved.
//

#import <Foundation/Foundation.h>
typedef NS_ENUM(NSUInteger, AKTransferType) {
    /** No data sent or received (no lc, data, le ) */
    AKTransferTypeNoData = 0,
    
    /** Data received (no lc) */
    AKTransferTypeReceiveData,
    
    /** Data sent (no le) */
    AKTransferTypeSendData,
    
    /** Data sent & received */
    AKTransferTypeSendReceiveData,
};


/**
 */
@interface AKCommandAPDU : NSObject

/**
 @name Properties
 */

/** Class */
@property (nonatomic) unsigned char cls;

/** Instruction */
@property (nonatomic) unsigned char ins;

/** Parameter 1 */
@property (nonatomic) unsigned char p1;

/** Parameter 2 */
@property (nonatomic) unsigned char p2;

/** Length of data */
@property (nonatomic) unsigned char Lc;

/** Data sent */
@property (nonatomic, strong) NSData * data;

/** Maximum number of bytes expected in resposne */
@property (nonatomic) unsigned char Le;

@property (nonatomic) AKTransferType type;

/**
 @name Methods
 */

-(NSString*)hexStringRepresentation;

/**
 Gets an instance of AKCommandAPDU

 @param aCls Class
 @param anIns Instruction
 @param aP1 Parameter 1
 @param aP2 Parameter 2
 @param aData Data Send
 @param aLe Max Length
 @return AKCommandAPDU
 */
+ (instancetype)withCls:(unsigned char)aCls
                    ins:(unsigned char)anIns
                     p1:(unsigned char)aP1
                     p2:(unsigned char)aP2
                   data:(NSData*)aData
                     Le:(unsigned char)aLe;

@end
