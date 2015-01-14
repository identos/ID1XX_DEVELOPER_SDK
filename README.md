
## IDENTOS
#### www.identos.com
###### Copyright 2015

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
### Using the Framework

#### ID100 Terminal Status

```[[AppIdKeyController sharedInstance] isDeviceConnected]```

#### Smart Card Status

```[[AppIdKeyController sharedInstance] isCardAvailable]```

#### Read Card Type

``` 
switch( [[AppIdKeyController sharedInstance] cardType] ) { 
	case AKCardTypeCredit:
		break;
	case AKCardTypeHealth:
		break;
	case AKNoCard:
	default:
		break;
}
```

#### Read Card Data
```
AKCard * card = [[AppIdKeyController sharedInstance] readCard];
```

#### Read Health Card Certificate 

```
AKHealthCard * healthCard;
NSData * cert = [healthCard readCertificate];
```

#### Read Credit Card Track2 Data
```
AKBankCard * bankCard;
if( bankCard.type == AKCreditCard ) {
	[bankCard readTrack2Data];
	NSData * track2 = bankCard.track2Data;
}
```



