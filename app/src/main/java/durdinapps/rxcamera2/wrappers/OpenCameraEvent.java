package durdinapps.rxcamera2.wrappers;


import durdinapps.rxcamera2.RxCameraDevice;

public class OpenCameraEvent {
    public final RxCameraDevice cameraDevice;
    public final EventType eventType;

    public OpenCameraEvent(RxCameraDevice cameraDevice, EventType eventType) {
        this.cameraDevice = cameraDevice;
        this.eventType = eventType;
    }

    public enum EventType {
        OPENED,
        DISCONNECTED,
        ERROR,
        CLOSED;
    }
}
