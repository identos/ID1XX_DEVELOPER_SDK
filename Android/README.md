# ID100 SDK Guide
This guide is meant to help implement the ID100 SDK for Android into an Android app. It covers topics like querying readers, communicating with them and reading data using helper classes.

As of today, the ID100 SDK for Android supports German health card cards and Belgian identity cards. However, the developer can implment a `CardHelper` to implement communication with new card types.


## Prerequisites
The SDK uses the Android USB APIs, therefore it only supports API level 13 (Android 3.1) and higher. The device must support USB Host, which can be specified in the *AndroidManifest.xml*

```xml
<uses-feature
    android:name="android.hardware.usb.host"
    android:required="true" />
```

## Example app
A fully functional implementation of the SDK is provided through the demo app. Please refer to this example to see how the library is used.

The example app demonstrates the reading of both the German health card and the Belgian identity card. Furthermore it shows how the readers can be queried using the default Android Intents.

## Connecting
### Quering readers
Follow the instructions at http://developer.android.com/guide/topics/connectivity/usb/host.html to query devices attached to the smartphone/tablet. It it is recommended to use the following hardware IDs to filter the devices.

```xml
<resources>
	<usb-device
	    vendor-id="9222"
	    product-id="20483"/>
</resources>
```

Once the `UsbDevice` has been attached it is required to handle the permission receiving in the app, which is explained in the instructions noted above.

The SDK uses the `UsbReader` class to represent an attached reader. A reader can be created with an `UsbDevice` associated to the reader.

```java
UsbDevice device = ...
UsbReader reader = new UsbReader(device);
```

Alternatively, all attached readers can be queried using the following method.

```java
List<UsbReader> readers = UsbReader.getUsbReaders(usbManager);

List<UsbReader> readers = UsbReader.getUsbReaders(context);
```

To check if an `UsbDevice` is a supported reader, use the following method.

```java
UsbDevice device = ...
boolean isSupported = UsbReader.isSupportedReader(device);
```

### Opening/Closing
After the `UsbReader` has been initialized, please ensure to open the reader with `UsbReader.open(context)` and close it with `UsbReader.close()` after using it. It is recommended to do this in the *Activity's/Fragment's lifecycle methods*.

```java
@Override
protected void onResume() {
	super.onResume();

	try {
		reader.open(context);
	} catch (IOException e) {
  	    // failed to open reader
    }
}

@Override
protected void onPause() {
	super.onPause();

	reader.close();
}
```
### Card events
After the `UsbReader` has been opened, it is recommended to check if a card id present. 

```java
boolean present = reader.isCardPresent();
```

If no card is present, the reader can wait for a card insertion. This can be achieved using multiple approaches. The method `waitForCardEvent(event)` waits synchronously for an event.

```java
UsbReader reader = ...
reader.waitForCardEvent(CcidReader.CARD_EVENT_CARD_INSERTED);

// Wait with a timeout, e.g. 5s
reader.waitForCardEvent(CcidReader.CARD_EVENT_CARD_REMOVED, 5000);
```

Waiting for card events synchronously on the main thread should be **avoided** at all costs as it blocks the thread. An alternative to that is registering an `OnCardEventListener` to the reader, which will be called if an event occurs.

```java
private final OnCardEventListener onCardEventListener = new OnCardEventListener() {
    
    @Override
    public void onCardEvent(CcidReader reader, int event) {
        switch (event) {
            case CcidReader.CARD_EVENT_CARD_REMOVED:
                // card removed
                break;

            case CcidReader.CARD_EVENT_CARD_INSERTED:
            case CcidReader.CARD_EVENT_CARD_PRESENT:
                // card present, read card
                break;
        }
    }
};

reader.addOnCardEventListener(onCardEventListener);

```

It is recommended to add/remove this listeners in the *Activity's/Fragment's lifecycle methods*.

```java
@Override
protected void onResume() {
	super.onResume();

	reader.addOnCardEventListener(onCardEventListener);
}

@Override
protected void onPause() {
	super.onPause();

	reader.removeOnCardEventListener(onCardEventListener);
}
```

### Communicating

The ID100 SDK supports the transmission of APDUs through the `UsbReader` class. Before the APDU command is sent, make sure a subclass extending `CardHelper` is made. The subclass need to call the protected `preparedCard()` method in `CardHelper` before the command is sent.

```java
byte[] rpdu = ccidReader.transmit(apdu);
```

**Caution**: This functionality is meant for advanced use cases only. Please consider the provided helpers first.

## Reading

Once a card is present, it can be read using one of the provided helpers. To identify the type of a card, a `CardHelper` should be used. It can be created using the following method.

```java
CardHelper helper = CardHelper.createCardHelper(this, reader, null);
```

After creating the `helper`, the `CardInfo` should be read. This can be done both synchronously and asynchronously.

>It is recommended to perform the reading operations asynchronously, as the main thread shouldn't be blocked while reading a card.

```java
CardInfo info = helper.readCardInfo();

helper.readCardInfo(new CardHelper.ReadCardInfoAsyncListener() {
            @Override
            public void onCardInfoRead(CardInfo cardInfo) {
                // the card info
            }

            @Override
            public void onError(int errorCode) {
                // an error occurred
            }
        });
```

After reading the `CardInfo`, a new helper specific to the card type should be created, as follows.

```java
CardInfo info = ...
CardHelper helper = CardHelper.createCardHelper(this, reader, info);
```

This helper can then be used to read the card. This can be done both synchronously and asynchronously.

```java
Card card = helper.readCard();

helper.readCard(new CardHelper.ReadCardAsyncListener() {
            @Override
            public void onCardRead(Card card) {
                // the card
            }

            @Override
            public void onError(int errorCode) {
                // an error occurred
            }
        });
```

#### Errors while reading

If a reading operation returns `null`, the last occurred error can be accessed using the following method.

```java
int errorCode = reader.getErrorCode();
```

Please see the JavaDoc files for the errors that can occur while reading the card.

## Logging

The ID100 SDK offers the logging of messages through `Logger` classes. Two logger implementations are provided, a `ConsoleLogger` to log to the standard Android console and a `FileLogger` to log messages to a file. These loggers need to be attached to a reader.

```java
ConsoleLogger logger = new ConsoleLogger(tag);

reader.attachLogger(logger);
```

Custom loggers, for instance a network logger, can be created by extending the `Logger` class.

### Log levels

The ID100 SDK uses log levels to provide multiple stages of logging. These log levels can be specified using the `setLogLevel` method.

```java
// log levels can used as flags
reader.setLogLevel(CcidReader.LOG_LEVEL_APDU | CcidReader.LOG_LEVEL_USB);
```

As per default, the ID100 SDK logs all messages using `LOG_LEVEL_ALL`.

## Documentation

For further information please see the provided example app and JavaDoc files.