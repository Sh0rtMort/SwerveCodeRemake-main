package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.*;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final XboxController driver = new XboxController(0);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kStart.value);

    private final JoystickButton intakeSuckButton = new JoystickButton(driver, 5);
    private final JoystickButton intakeSpitButton = new JoystickButton(driver, 6);
    private final JoystickButton armUpButton = new JoystickButton(driver, Button.kX.value);
    private final JoystickButton armDownButton = new JoystickButton(driver, Button.kB.value);
    // private final JoystickButton armDownButton1 = new JoystickButton(driver, XboxController.Axis.kLeftTrigger.value);
    // private final JoystickButton armPickupButton = new JoystickButton(driver, Button.kA.value);
    // private final JoystickButton armStoreButton = new JoystickButton(driver, Button.kB.value);
    private final JoystickButton armPlaceButton = new JoystickButton(driver, Button.kA.value);

    
    private final Swerve s_Swerve = new Swerve();
    private final ArmSubsystem armSubsystem = new ArmSubsystem();

    private final IntakeCommand intakeSuck = new IntakeCommand(armSubsystem, 0.8);
    private final IntakeCommand intakeSpit = new IntakeCommand(armSubsystem, -0.95);

    private final ArmManualCommand armUp = new ArmManualCommand(armSubsystem, -0.8);
    private final ArmManualCommand armDown = new ArmManualCommand(armSubsystem, 0.8);

   // private final ArmPositionCommand armPickupPosition = new ArmPositionCommand(armSubsystem, 15000);
   // private final ArmPositionCommand armStorePosition = new ArmPositionCommand(armSubsystem, 5000);
   private final ArmPositionCommand armMiddleConePlace = new ArmPositionCommand(armSubsystem, 60);


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        // Configure the button bindings
        configureButtonBindings();
        armSubsystem.zeroAllEncoders();
    }

    
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        // zeroGyro.onTrue(new InstantCommand(() -> armSubsystem.zeroAllEncoders()));
        
        intakeSuckButton.whileTrue(intakeSuck);
        intakeSpitButton.whileTrue(intakeSpit);

        armDownButton.whileTrue(armDown);
        armUpButton.whileTrue(armUp);

        //armStoreButton.whileTrue(armStorePosition);
        //armPickupButton.whileTrue(armPickupPosition);
       armPlaceButton.whileTrue(new SequentialCommandGroup(

        ));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve);
    }
}
