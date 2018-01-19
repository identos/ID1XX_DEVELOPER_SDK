//
//  AKTerminalCommand.h
//  AppIdDemo
//
//  Created by Alec Laws on 2014-05-16.
//  Copyright (c) 2014 Spoke Technologies. All rights reserved.
//

#import <Foundation/Foundation.h>


/**

 - AKTerminalCommandPending: Command Pending
 - AKTerminalCommandSuccess: Command Success
 - AKTerminalCommandNoCard: No Card Command
 - AKTerminalCommandNoValidCard: No Valid Card Command
 - AKTerminalCommandError: Error Command
 */
typedef  NS_ENUM(NSUInteger, AKTerminalCommandStatus) {
    AKTerminalCommandPending =0,
    AKTerminalCommandSuccess,
    AKTerminalCommandNoCard,
    AKTerminalCommandNoValidCard,
    AKTerminalCommandError
};

/**
 
 Provides an interface to control & send data to the smart card
 */
@interface AKCardCommand : NSObject {
    unsigned char _command;
    AKTerminalCommandStatus _status;
}

/**
 @name Properties
 */

/**
 The command for the terminal to process
 */
@property (nonatomic, readonly) unsigned char command;


/**
 The status of the Card Command
 
 The status will be AKTerminalCommandPending until the command is attempted
 */
@property (nonatomic, readwrite) AKTerminalCommandStatus status;

/**
 @name Methods
 */

-(void)updateStatus:(AKTerminalCommandStatus)status;

-(NSData*)dataRepresentation;

-(NSString*)hexStringRepresentation;

-(NSData*)processResponseData:(NSData*)resposne;


/**
 The terminal Command

 @param c Terminal Command Byte
 @return return AKTerminalCommand
 */
+(instancetype)terminalCommand:(unsigned char)c;

@end

