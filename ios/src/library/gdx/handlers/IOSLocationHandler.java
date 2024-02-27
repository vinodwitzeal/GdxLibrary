package library.gdx.handlers;

import org.robovm.apple.corelocation.CLAuthorizationStatus;
import org.robovm.apple.corelocation.CLBeacon;
import org.robovm.apple.corelocation.CLBeaconIdentityConstraint;
import org.robovm.apple.corelocation.CLBeaconRegion;
import org.robovm.apple.corelocation.CLHeading;
import org.robovm.apple.corelocation.CLLocation;
import org.robovm.apple.corelocation.CLLocationManager;
import org.robovm.apple.corelocation.CLLocationManagerDelegate;
import org.robovm.apple.corelocation.CLRegion;
import org.robovm.apple.corelocation.CLRegionState;
import org.robovm.apple.corelocation.CLVisit;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBooleanBlock;

public class IOSLocationHandler implements CLLocationManagerDelegate {

    private VoidBooleanBlock handler;

    public IOSLocationHandler(VoidBooleanBlock handler){
        this.handler=handler;
    }

    @Override
    public void didUpdateLocations(CLLocationManager manager, NSArray<CLLocation> locations) {

    }

    @Override
    public void didUpdateHeading(CLLocationManager manager, CLHeading newHeading) {

    }

    @Override
    public boolean shouldDisplayHeadingCalibration(CLLocationManager manager) {
        return false;
    }

    @Override
    public void didDetermineState(CLLocationManager manager, CLRegionState state, CLRegion region) {

    }

    @Override
    public void didRangeBeacons(CLLocationManager manager, NSArray<CLBeacon> beacons, CLBeaconRegion region) {

    }

    @Override
    public void rangingBeaconsDidFail(CLLocationManager manager, CLBeaconRegion region, NSError error) {

    }

    @Override
    public void didRangeBeacons(CLLocationManager manager, NSArray<CLBeacon> beacons, CLBeaconIdentityConstraint beaconConstraint) {

    }

    @Override
    public void didFailRangingBeacons(CLLocationManager manager, CLBeaconIdentityConstraint beaconConstraint, NSError error) {

    }

    @Override
    public void didEnterRegion(CLLocationManager manager, CLRegion region) {

    }

    @Override
    public void didExitRegion(CLLocationManager manager, CLRegion region) {

    }

    @Override
    public void didFail(CLLocationManager manager, NSError error) {

    }

    @Override
    public void monitoringDidFail(CLLocationManager manager, CLRegion region, NSError error) {

    }

    @Override
    public void didChangeAuthorizationStatus(CLLocationManager manager, CLAuthorizationStatus status) {
        if (handler!=null){
            if (status==CLAuthorizationStatus.AuthorizedAlways || status==CLAuthorizationStatus.AuthorizedWhenInUse){
                handler.invoke(true);
            }else {
                handler.invoke(false);
            }
        }
    }

    @Override
    public void locationManagerDidChangeAuthorization(CLLocationManager manager) {

    }

    @Override
    public void didStartMonitoring(CLLocationManager manager, CLRegion region) {

    }

    @Override
    public void didPauseLocationUpdates(CLLocationManager manager) {

    }

    @Override
    public void didResumeLocationUpdates(CLLocationManager manager) {

    }

    @Override
    public void didFinishDeferredUpdates(CLLocationManager manager, NSError error) {

    }

    @Override
    public void didVisit(CLLocationManager manager, CLVisit visit) {

    }
}
