// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ArmManualCommand extends CommandBase {

  private ArmSubsystem armSubsystem;
  private double armSpeed;

  public ArmManualCommand(ArmSubsystem armSubsystem, double armSpeed) {
    this.armSubsystem = armSubsystem;
    this.armSpeed = armSpeed;

  }

  
  @Override
  public void initialize() {

  }

  
  @Override
  public void execute() {
    armSubsystem.setWinchSpeed(armSpeed);
  }

  
  @Override
  public void end(boolean interrupted) {
    armSubsystem.setWinchSpeed(0);
  }

 
}
