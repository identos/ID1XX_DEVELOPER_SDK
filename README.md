


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



