package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.Swerve;

public class ChargeBalance extends CommandBase{
    private final Swerve s_Swerve;
    private final PIDController xPidController;
    private Translation2d translation2d;
    private Translation2d eTranslation2d; //ending translation

    public ChargeBalance(Swerve s_Swerve) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.xPidController = new PIDController(0.05, 0,0);
        xPidController.setTolerance(100);
        xPidController.setSetpoint(0);
    }

    @Override
    public void execute() {
      System.out.println("Auto Balance Started");
        double xSpeed = xPidController.calculate(s_Swerve.getRoll());
        translation2d = new Translation2d(-xSpeed, 0);
        s_Swerve.drive(translation2d, 0, true, true);

        SmartDashboard.putNumber("Roll", s_Swerve.getRoll());
        SmartDashboard.putNumber("xSpeed", xSpeed);
    }

    @Override
  public void end(boolean interrupted) {
    eTranslation2d = new Translation2d(0, 0);
    s_Swerve.drive(eTranslation2d, 0, true, true);
    System.out.println("Auto Balance Stopped!!!!!"); //Prints to the console
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}