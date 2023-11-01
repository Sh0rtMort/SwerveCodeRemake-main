// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.ArmSubsystem;

public class ArmPositionCommand extends CommandBase {
  
  private ArmSubsystem armSubsystem;
  private double setpoint;
  private PIDController winchPIDController;

  public ArmPositionCommand(ArmSubsystem armSubsystem, double setpoint) {
    addRequirements(armSubsystem);
    this.armSubsystem = armSubsystem;
    this.setpoint = setpoint;

    this.winchPIDController = new PIDController(0.001, 0.002, 0);
    winchPIDController.setTolerance(1);
    winchPIDController.setSetpoint(setpoint);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  
  @Override
  public void execute() {
    double winchSpeed = winchPIDController.calculate(armSubsystem.getWinchAngle());
    armSubsystem.setWinchSpeed(winchSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
