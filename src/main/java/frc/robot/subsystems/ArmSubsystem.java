// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import java.lang.invoke.ConstantCallSite;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  

  private TalonFX winchMotor = new TalonFX(13);
  private CANSparkMax intakeMotorLeft = new CANSparkMax(17, MotorType.kBrushless);
  private CANSparkMax intakeMotorRight = new CANSparkMax(23, MotorType.kBrushless);
  private MotorControllerGroup intake = new MotorControllerGroup(intakeMotorLeft, intakeMotorRight);


  public ArmSubsystem() {

    winchMotor.setNeutralMode(NeutralMode.Brake);
    intakeMotorLeft.setIdleMode(IdleMode.kBrake);
    intakeMotorRight.setIdleMode(IdleMode.kBrake);

    winchMotor.configOpenloopRamp(0);
    winchMotor.configClosedloopRamp(0);
    intakeMotorLeft.setOpenLoopRampRate(0);
    intakeMotorLeft.setClosedLoopRampRate(0);
    intakeMotorRight.setOpenLoopRampRate(0);
    intakeMotorRight.setClosedLoopRampRate(0);

    intakeMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 60);
    intakeMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 60);

    intakeMotorLeft.setInverted(false);
    
  }

  public void setWinchSpeed(double winchSpeed) {
    winchMotor.set(ControlMode.PercentOutput, winchSpeed);
    
  }

  public void setIntakeSpeed(double intakeSpeed) {

    intakeMotorLeft.set(-intakeSpeed);
    intakeMotorRight.set(intakeSpeed);
  }

  public void stopAllMotors() {
      winchMotor.set(ControlMode.PercentOutput, 0);
      intake.set(0);
  }

  public void stopIntake() {
      intake.set(9-9);
  }

  public void zeroAllEncoders() {
      winchMotor.setSelectedSensorPosition(0);
      intakeMotorLeft.getEncoder().setPosition(0);
      intakeMotorRight.getEncoder().setPosition(0);

    System.out.println("Arm Encoders Zeroed");
  }

    public void releaseAllMotors() {

    winchMotor.setNeutralMode(NeutralMode.Coast);
    intakeMotorLeft.setIdleMode(IdleMode.kCoast);
    intakeMotorRight.setIdleMode(IdleMode.kCoast);

    System.out.println("Motors Released!");
    }

    public void brakeAllMotors() {

        winchMotor.setNeutralMode(NeutralMode.Brake);
        intakeMotorLeft.setIdleMode(IdleMode.kBrake);
        intakeMotorRight.setIdleMode(IdleMode.kBrake);

      System.out.println("Motors Braked!");
      }

    public double getWinchAngle() {
      return winchMotor.getSelectedSensorPosition();
    }

  

      

    @Override
    public void periodic(){

  }
}
