package durdinapps.rxcamera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.os.Handler;
import android.view.Surface;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import durdinapps.rxcamera2.wrappers.RxConfigureSessionEvent;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxCameraDeviceTest {

    private static int ANY_TEMPLATE_TYPE = 1;

    @Mock
    CameraDevice cameraDevice;

    @Mock
    CaptureRequest.Builder captureBuilder;

    @Mock
    Handler handler;

    @Mock
    CameraCaptureSession cameraCaptureSession;

    @Mock
    Surface surface;

    RxCameraDevice rxCameraDevice;

    @Mock
    RxConfigureSessionEvent rxConfigureSessionEvent;

    @Mock
    List<Surface> outputs;

    @Mock
    InputConfiguration inputConfiguration;

    @Mock
    List<OutputConfiguration> outputConfiguration;

    @Mock
    TotalCaptureResult totalCaptureResult;

    ArgumentCaptor<CameraCaptureSession.StateCallback> stateCallback = ArgumentCaptor.forClass(CameraCaptureSession.StateCallback.class);

    @Before
    public void setup() throws CameraAccessException {
        MockitoAnnotations.initMocks(this);

        rxCameraDevice = new RxCameraDevice(cameraDevice);

        when(cameraDevice.createCaptureRequest(ANY_TEMPLATE_TYPE)).thenReturn(captureBuilder);
        when(cameraDevice.createReprocessCaptureRequest(totalCaptureResult)).thenReturn(captureBuilder);
    }

    @Test
    public void createCaptureRequest() throws CameraAccessException {
        TestObserver<CaptureRequest.Builder> testDeviceManager =
                rxCameraDevice.createCaptureRequest(ANY_TEMPLATE_TYPE)
                        .test();

        verify(cameraDevice).createCaptureRequest(eq(ANY_TEMPLATE_TYPE));

        testDeviceManager.assertNoErrors()
                .assertValueSet(Collections.singletonList(captureBuilder))
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

    @Test
    public void createCaptureSession() throws CameraAccessException {
        TestObserver<RxConfigureSessionEvent> testDeviceManager =
                rxCameraDevice.createCaptureSession(outputs, handler)
                        .test();

        verify(cameraDevice).createCaptureSession(eq(outputs), stateCallback.capture(), eq(handler));

        stateCallback.getValue().onConfigured(cameraCaptureSession);

        testDeviceManager.assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

    @Test
    public void createCaptureSessionFailed() throws CameraAccessException {
        TestObserver<RxConfigureSessionEvent> testDeviceManager =
                rxCameraDevice.createCaptureSession(outputs, handler)
                        .test();

        verify(cameraDevice).createCaptureSession(eq(outputs), stateCallback.capture(), eq(handler));

        stateCallback.getValue().onConfigureFailed(cameraCaptureSession);

        testDeviceManager.assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

    @Test
    public void createConstrainedHighSpeedCaptureSession() throws CameraAccessException {
        TestObserver<RxConfigureSessionEvent> testDeviceManager =
                rxCameraDevice.createConstrainedHighSpeedCaptureSession(outputs, handler)
                        .test();

        verify(cameraDevice).createConstrainedHighSpeedCaptureSession(eq(outputs), stateCallback.capture(), eq(handler));

        stateCallback.getValue().onConfigured(cameraCaptureSession);

        testDeviceManager.assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

    @Test
    public void createConstrainedHighSpeedCaptureSessionFailed() throws CameraAccessException {
        TestObserver<RxConfigureSessionEvent> testDeviceManager =
                rxCameraDevice.createConstrainedHighSpeedCaptureSession(outputs, handler)
                        .test();

        verify(cameraDevice).createConstrainedHighSpeedCaptureSession(eq(outputs), stateCallback.capture(), eq(handler));

        stateCallback.getValue().onConfigureFailed(cameraCaptureSession);

        testDeviceManager.assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

    @Test
    public void createCaptureSessionByOutputConfigurations() throws CameraAccessException {
        TestObserver<RxConfigureSessionEvent> testDeviceManager =
                rxCameraDevice.createCaptureSessionByOutputConfigurations(outputConfiguration, handler)
                        .test();

        verify(cameraDevice).createCaptureSessionByOutputConfigurations(eq(outputConfiguration), stateCallback.capture(), eq(handler));

        stateCallback.getValue().onConfigured(cameraCaptureSession);

        testDeviceManager.assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

    @Test
    public void createCaptureSessionByOutputConfigurationsFailed() throws CameraAccessException {
        TestObserver<RxConfigureSessionEvent> testDeviceManager =
                rxCameraDevice.createCaptureSessionByOutputConfigurations(outputConfiguration, handler)
                        .test();

        verify(cameraDevice).createCaptureSessionByOutputConfigurations(eq(outputConfiguration), stateCallback.capture(), eq(handler));

        stateCallback.getValue().onConfigureFailed(cameraCaptureSession);

        testDeviceManager.assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

    @Test
    public void createReprocessCaptureRequest() throws CameraAccessException {
        TestObserver<CaptureRequest.Builder> testDeviceManager =
                rxCameraDevice.createReprocessCaptureRequest(totalCaptureResult)
                        .test();

        verify(cameraDevice).createReprocessCaptureRequest(eq(totalCaptureResult));

        testDeviceManager.assertNoErrors()
                .assertValueSet(Collections.singletonList(captureBuilder))
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

    @Test
    public void createReprocessableCaptureSession() throws CameraAccessException {
        TestObserver<RxConfigureSessionEvent> testDeviceManager =
                rxCameraDevice.createReprocessableCaptureSession(inputConfiguration, outputs, handler)
                        .test();

        verify(cameraDevice).createReprocessableCaptureSession(eq(inputConfiguration), eq(outputs), stateCallback.capture(), eq(handler));

        stateCallback.getValue().onConfigured(cameraCaptureSession);

        testDeviceManager.assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

    @Test
    public void createReprocessableCaptureSessionFailed() throws CameraAccessException {
        TestObserver<RxConfigureSessionEvent> testDeviceManager =
                rxCameraDevice.createReprocessableCaptureSession(inputConfiguration, outputs, handler)
                        .test();

        verify(cameraDevice).createReprocessableCaptureSession(eq(inputConfiguration), eq(outputs), stateCallback.capture(), eq(handler));

        stateCallback.getValue().onConfigureFailed(cameraCaptureSession);

        testDeviceManager.assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
                .dispose();
    }

}

