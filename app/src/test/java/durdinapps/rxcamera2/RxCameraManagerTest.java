package durdinapps.rxcamera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import durdinapps.rxcamera2.wrappers.OpenCameraEvent;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class RxCameraManagerTest {

    private static String ANY_CAMERA_ID = "0";
    private static int ANY_SOME_ERROR = 4;
    private static boolean ANY_BOOLEAN = false;

    RxCameraManager rxCameraManager;

    @Mock
    CameraManager cameraManager;

    @Mock
    CameraDevice cameraDevice;

    @Mock
    Handler handler;

    @Mock
    OpenCameraEvent openCameraEvent;

    ArgumentCaptor<CameraDevice.StateCallback> stateCallback = ArgumentCaptor.forClass(CameraDevice.StateCallback.class);
    ArgumentCaptor<CameraManager.AvailabilityCallback> availabilityCallback = ArgumentCaptor.forClass(CameraManager.AvailabilityCallback.class);
    ArgumentCaptor<CameraManager.TorchCallback> torchCallback = ArgumentCaptor.forClass(CameraManager.TorchCallback.class);

    @Before
    public void setup() throws CameraAccessException {
        MockitoAnnotations.initMocks(this);

        rxCameraManager = new RxCameraManager(cameraManager);
//        when(stateCallback.);)
    }

    @Test
    public void openCamera() throws CameraAccessException {
        TestObserver<OpenCameraEvent> testManagerObserver =
                rxCameraManager.openCamera(ANY_CAMERA_ID, handler)
                        .test();

        verify(cameraManager, atLeastOnce()).openCamera(eq(ANY_CAMERA_ID), stateCallback.capture(), eq(handler));

        stateCallback.getValue().onOpened(cameraDevice);
        stateCallback.getValue().onDisconnected(cameraDevice);
        stateCallback.getValue().onError(cameraDevice, ANY_SOME_ERROR);
        stateCallback.getValue().onClosed(cameraDevice);

        testManagerObserver.assertNoErrors()
                .assertValueCount(4)
                .dispose();
    }

    @Test
    public void registerAvailabilityCallback() {
        TestObserver<Boolean> testManagerObserver =
                rxCameraManager.registerAvailabilityCallback(handler)
                        .test();

        verify(cameraManager, atLeastOnce()).registerAvailabilityCallback(availabilityCallback.capture(), eq(handler));

        availabilityCallback.getValue().onCameraAvailable(ANY_CAMERA_ID);
        availabilityCallback.getValue().onCameraUnavailable(ANY_CAMERA_ID);

        testManagerObserver.assertNoErrors()
                .assertValueCount(2)
                .dispose();
    }

    @Test
    public void registerTorchCallback() {
        TestObserver<Boolean> testManagerObserver =
                rxCameraManager.registerTorchCallback(handler)
                        .test();

        verify(cameraManager, atLeastOnce()).registerTorchCallback(torchCallback.capture(), eq(handler));

        torchCallback.getValue().onTorchModeChanged(ANY_CAMERA_ID, ANY_BOOLEAN);
        torchCallback.getValue().onTorchModeUnavailable(ANY_CAMERA_ID);

        testManagerObserver.assertNoErrors()
                .assertValueCount(2)
                .dispose();
    }

    @Test
    public void setTorchMode() throws CameraAccessException {
        TestObserver<Void> testManagerObserver =
                rxCameraManager.setTorchMode(ANY_CAMERA_ID, ANY_BOOLEAN)
                        .test();

        verify(cameraManager, atLeastOnce()).setTorchMode(eq(ANY_CAMERA_ID), eq(ANY_BOOLEAN));

        testManagerObserver.assertNoErrors()
                .assertComplete()
                .dispose();
    }
}
