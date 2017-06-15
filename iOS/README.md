
## IDENTOS
#### www.identos.com
###### Copyright 2017

### Overview
This framework is provided by IDENTOS for use exclusively with the APPIDKEY ID100 Card Reader, and is governed by the terms of use granted by license to users of the ID100 product. 

Any feedback provided on this framework via the Github issue tracking will be review, prioritized, and issues resolved in future releases.  Should you need more urgent support, please contact IDENTOS directly via www.identos.com

Please select 'Watch' on this repository for the latest updates

### Install the Framework

1. Download the SDK
2. Drag the `AppIdKey.framework` into your project
3. In the `Info.plist`, add the value `com.scapiscreader.iOS.a.a.a` for the `Supported external accessory protocols` key (*)
3. Link with the `ExternalAccessory.framework`
	1. Click your project in the navigator
	2. Go to your `Target` > `Build Phases`
	3. Hit the + under `Link Binary With Libraries`
	4. Find the `ExternalAccessory.framework` and hit `Add`
4. `#import <AppIdKey/AppIdKey.h>`

##### *Info.plist Protocol

```
<key>UISupportedExternalAccessoryProtocols</key>
<array>
	<string>com.scapiscreader.iOS.a.a.a</string>
</array>
```

##### AppIdKey in Swift

AppIdKey umbrella header (AppIdKey.h) must be used as the bridging header.

1. Go to project `Build Settings`
2. Search for key `Objective-C Bridging Header`
3. Set key to `$(SRCROOT)/AppIdKey.framework/Headers/AppIdKey.h`

```
//:configuration = Debug
SWIFT_OBJC_BRIDGING_HEADER[arch=*] = $(SRCROOT)/AppIdKey.framework/Headers/AppIdKey.h

//:configuration = Release
SWIFT_OBJC_BRIDGING_HEADER[arch=*] = $(SRCROOT)/AppIdKey.framework/Headers/AppIdKey.h

//:completeSettings = some
SWIFT_OBJC_BRIDGING_HEADER
```

### Using the Framework

#### Terminal Presence

##### Current Status

`[[AppIdKeyController sharedInstance] isDeviceConnected]`


##### Observers

```
[[AppIdKeyController sharedInstance] setOnDeviceConnected:^{
	// device was connected
}];

[[AppIdKeyController sharedInstance] setOnDeviceDisconnected:^{
	// device was disconnected
}];
```


#### Physical Interface

##### Smart Card Status

`[[AppIdKeyController sharedInstance] isCardAvailable]`

##### Polling

Completion is only run when card status changes (inserted or removed). When a card is inserted, completion will return with ^(AKCard, error). When card is removed, completion will return with ^(nil, nil). 
```    
[[AppIdKeyController sharedInstance] startPolling:^(AKCard* card, NSError * error) {
	// Do Something with the card
}];
```

```
[[AppIdKeyController sharedInstance] stopPolling];
```

##### Read Card Type

``` 
switch( [[AppIdKeyController sharedInstance] cardType] ) { 
	case AKCardTypeCredit:
		break;
	case AKCardTypeHealth:
		break;
	case AKNoCard:
		break;
	default:
		break;
}
```

##### Read Card Data
```
AKCard * card = [[AppIdKeyController sharedInstance] readCard];
```

##### Read Health Card Certificate 

```
AKHealthCard * healthCard;
NSData * cert = [healthCard readCertificate];
```

##### Read Credit Card Track2 Data
```
AKBankCard * bankCard;
if( bankCard.type == AKCreditCard ) {
	[bankCard readTrack2Data];
	NSData * track2 = bankCard.track2Data;
}
```

##### APDU 

```
// example test for Credit Card
NSData * data = [@"1PAY.SYS.DDF01" dataUsingEncoding:NSASCIIStringEncoding];
AKCommandAPDU * apdu = [AKCommandAPDU withCls:0x0 ins:0xA4 p1:0x04 p2:0x0 data:data Le:0x0];
apdu.type = AKTransferTypeSendReceiveData;

AKTransferCommand *transfer = [AKTransferCommand transferAPDU:apdu];

NSData * data = [[AppIdKeyController sharedInstance] performCommand:transfer];

if (transfer.status == AKTerminalCommandSuccess) {
	// terminal successfully transferred to card
	
	if([transfer.response status] == AKResponseNormal) {
		// card responded nornally
		ISLog(@"command success (%02X %02X %@)", transfer.response.sw1, transfer.response.sw2, transfer.response.data);
	}
}
else {
	ISLog(@"terminal failed (%lu)", (unsigned long)transfer.status);
}

```		

#### NFC Interface


##### Card Discovery (+ Read CC Example)

```

[[AppIdKeyController sharedInstance] startDiscovery:^(id<AKNFCCard> card, NSError *error) {

	// example test for Credit Card
	NSData * paysys = [@"1PAY.SYS.DDF01" dataUsingEncoding:NSASCIIStringEncoding];
	AKCommandAPDU *apdu = [AKCommandAPDU withCls:0x0 ins:0xA4 p1:0x04 p2:0x0 data:paysys Le:0x0];
	apdu.type = AKTransferTypeSendReceiveData;
	
	AKNFCTransferCommand *transfer = [AKNFCTransferCommand transferAPDU:apdu];
	NSData * data = [card performCommand:transfer];
	ISLog(@"NFC 1PAY.SYS.DDF01: %@", data);        
}];

```

##### Stop Discovery

`[[AppIdKeyController sharedInstance] stopDiscovery];`

