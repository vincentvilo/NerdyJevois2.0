package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Live vision tracking (follows vision target around, no end)
 * 
 * @author tedlin
 *
 */

public class LiveTargetTracking extends Command {

    private double kP = 0.013;

    /**
     * @param isHighGear
     */
    public LiveTargetTracking() {
	requires(Robot.drive);
	requires(Robot.jevois);
    }

    @Override
    protected void initialize() {   
	SmartDashboard.putString("Current Command", "LiveTargetTracking");
    }

    @Override
    protected void execute() {
	double relativeAngleError = Robot.jevois.getAngularTargetError(); 
									 
	double power = kP * -relativeAngleError;
	
	if (Math.abs(relativeAngleError) <= Constants.kDriveRotationDeadband || (Math.abs(relativeAngleError) >= 25)) {
	    power = 0;
	} else {
	    Robot.drive.setPower(power, power);
	}
	SmartDashboard.putNumber("Rotational Power", power);

    }

    @Override
    protected boolean isFinished() {
	return false;

    }

    @Override
    protected void end() {
	Robot.drive.stopDrive();
    }

    @Override
    protected void interrupted() {
	end();
    }
}