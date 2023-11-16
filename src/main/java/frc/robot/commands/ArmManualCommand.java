// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ArmManualCommand extends CommandBase {

  private ArmSubsystem armSubsystem;
  private double armSpeed;
  private XboxController gamepad;

  public ArmManualCommand(XboxController gamepad, ArmSubsystem armSubsystem, double armSpeed) {
    this.armSubsystem = armSubsystem;
    this.armSpeed = armSpeed;
    this.gamepad = gamepad;
    addRequirements(armSubsystem);

  }

  
  @Override
  public void initialize() {

  }

  
  @Override
  public void execute() {
    //RightX is turning so adjust for joysticks

    double triggerDeadband = 0.5;

    //using right joystick manual
    // armSubsystem.setWinchSpeed(gamepad.getRightY() * armSpeed);

    //using triggers to manual control
    if (gamepad.getLeftTriggerAxis() > triggerDeadband) {
        armSubsystem.setWinchSpeed(-armSpeed);
    } else if (gamepad.getRightTriggerAxis() > triggerDeadband) {
        armSubsystem.setWinchSpeed(armSpeed);
    } else {
      armSubsystem.stopWinch();
    }
  }

  
  @Override
  public void end(boolean interrupted) {
    armSubsystem.stopWinch();
  }
}
