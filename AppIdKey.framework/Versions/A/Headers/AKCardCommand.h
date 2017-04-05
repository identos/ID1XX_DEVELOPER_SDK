//
//  AKTerminalCommand.h
//  AppIdDemo
//
//  Created by Alec Laws on 2014-05-16.
//  Copyright (c) 2014 Spoke Technologies. All rights reserved.
//

#import <Foundation/Foundation.h>


typedef  NS_ENUM(NSUInteger, AKTerminalCommandStatus) {
    AKTerminalCommandPending =0,
    AKTerminalCommandSuccess,
    AKTerminalCommandNoCard,
    AKTerminalCommandNoValidCard,
    AKTerminalCommandError
};


/**
 @class AKCardCommand
 
 Provides an interface to control & send data to the smart card
 
 */
@interface AKCardCommand : NSObject {
    unsigned char _command;
    AKTerminalCommandStatus _status;
}
// the command for the terminal to process
@property (nonatomic, readonly) unsigned char command;


/**
 The status of the Card Command
 
 The status will be AKTerminalCommandPending until the command is attempted
 */
@property (nonatomic, readonly) AKTerminalCommandStatus status;


+(instancetype)terminalCommand:(unsigned char)c;
@end

