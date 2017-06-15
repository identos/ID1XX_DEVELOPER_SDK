//
//  ISLog.h
//  screader
//
//  Created by Ronja Lars Wilkening on 23.03.12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

#define kNotificationLogChanged @"log_changed_notification"

@interface ISLog : NSObject
{
    NSString* _logText;
}

@property (nonatomic, strong) NSString* logText;

+ (ISLog*) sharedLog;
- (void) log:(NSString*)message;
- (void) clear;

@end



#define ISLog(...) [[ISLog sharedLog] log:[NSString stringWithFormat:__VA_ARGS__]]
