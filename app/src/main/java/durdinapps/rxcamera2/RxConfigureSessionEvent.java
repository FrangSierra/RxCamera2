package durdinapps.rxcamera2;

import android.hardware.camera2.CameraCaptureSession;

public class RxConfigureSessionEvent {
    final CameraCaptureSession session;
    final EventType eventType;


    public RxConfigureSessionEvent(CameraCaptureSession session, EventType eventType) {
        this.session = session;
        this.eventType = eventType;
    }

    public static enum EventType {
        CONFIGURE,
        CONFIGURE_FAILED;

        private EventType() {
        }
    }
}
