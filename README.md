# RxCamera2
[Rxjava 2.0](https://github.com/ReactiveX/RxJava/tree/2.x) wrapper for Camera2 google API. Based [android.hardware.camera2](https://developer.android.com/reference/android/hardware/camera2/package-summary.html)

## Download

##### Gradle:

```groovy
dependencies {
     compile 'com.github.FrangSierra:RxCamera2:0.5'
}
```
```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

## Basic Usage

First of all you will need a RxCameraManager item to start to work. You can get it in the next way:
```java
 this.cameraManager = new RxCameraManager((CameraManager) app.getSystemService(Context.CAMERA_SERVICE));
```

You will need to do is open your camera for that you will need to call the method `openCamera` from `RxCameraManager`:
```java
cameraManager.openCamera(selectedCameraId,backgroundHandler)
                     .doOnTerminate(releaseCamera())
                     .subscribe(openCameraEvent -> {
                         switch (openCameraEvent.eventType){
                             case OPENED:
                             /** Manage your OpenCameraEvent which contains a CameraDevice object 
                              *  and a TAG to check if the event as been suscessful
                              */
                                 this.deviceManager = openCameraEvent.cameraDevice;
                                 break;
                             case DISCONNECTED:
                                 Log.d(TAG, "Camera disconnected: %s", openCameraEvent.cameraDevice.getCameraDevice().getId());
                                 break;
                             case ERROR:
                                 break;
                         }
                     });
```

## Creating a Session

To create a Session you first need to generate a `CaptureRequest` to add the target of your preview `Surface`, and all the different surfaces that you will use during the lyfecicle of the current session. 
```java
 cameraState.cameraDevice
 .createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
 .subscribe(builder -> this.builder = builder);
```
After that, you can create your CaptureSession :
```java
cameraState.cameraDevice
.createCaptureSession(Collections.singletonList(cameraState.previewSurface), builder, backgroundHandler)
.susbscribe(configureSessionEvent -> {
 RxConfigureSessionEvent rxConfigureSessionEvent = configureSessionEvent;
 switch (rxConfigureSessionEvent.eventType) {
                     case CONFIGURE:
                        try {
                           rxConfigureSessionEvent.session.getCaptureSession().getInputSurface();
                           //This call is irrelevant,
                           //however session might have closed and this will throw an IllegalStateException.
                           //This happens if another camera app (or this one in another PID) takes control
                           //of the camera while its opening
                        } catch (IllegalStateException e) {
                           Log.e("Another process took control of the camera while creating the session, aborting!");
                        }
                        this.rxCameraCaptureSession = rxConfigureSessionEvent.session;
                        rxConfigureSessionEvent.session.setRepeatingRequest(builder.build(), backgroundHandler).subscribe();
                        break;
 
                     case CONFIGURE_FAILED:
                         Log.e("On configure failed");
                        break;
                  }
                  //Now we are opening                 
               });
```
## Capturing photos

Finally you can start to capturing photos using the different methods of your current `RxCameraCaptureSession` object. Let's see an example using the `capture` method:

```java
capture(captureRequest,handler)
.subscribe(captureCallbackEvent -> {
           switch (captureCallbackEvent.eventType){
               case STARTED:
                   //Your capture as started, do what you want with your session,
                   // captureRequest, timestamp and frameNumber values.
                   break;
               case PROGRESSED:
                   //Your capture is in progress, check some conditions with your session,
                   // captureRequest,  or the different values from your partialResult.
                   break;
               case COMPLETED:
                   //Your capture as been completed. You will recieve the session, the used
                   // captureRequest and the exact CaptureResult values from your picture.
                   break;
               case FAILED:
                   //Your capture as failed, manage your error.
                   break;
               case SEQUENCE_COMPLETED:
                  // This method is called independently of the others in CaptureCallback,
                   // when a capture sequence finishes and all CaptureResult or
                   // CaptureFailure for it have been returned via this listener.
                   break;
               case SEQUENCE_ABORTED:
                   //This is called independently of the others in CaptureCallback,
                   // when a capture sequence aborts before any CaptureResult or
                   // CaptureFailure for it have been returned via this listener.
                   break;
               case BUFFER_LOST:
                   //API24 
                   //This method is called if a single buffer for a capture could
                   // not be sent to its destination surface.
                   break;
           }
        });
```
