package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autos.balanceAuto;
import frc.robot.autos.exampleAuto;
import frc.robot.autos.leaveComZone;
import frc.robot.commands.ArmManualCommand;
import frc.robot.commands.ArmPositionCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.Swerve;

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
    private final JoystickButton zeroGyro = new JoystickButton(driver, 7); //options button(two squares)
    private final JoystickButton zeroArmEncoder = new JoystickButton(driver, 7); //options button(two squares)
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kStart.value);

    private final JoystickButton intakeSuckButton = new JoystickButton(driver, 5);
    private final JoystickButton intakeSpitButton = new JoystickButton(driver, 6);
    //private final JoystickButton armPickupButton = new JoystickButton(driver, Button.kA.value);
    //private final JoystickButton armStoreButton = new JoystickButton(driver, Button.kY.value);
    //private final JoystickButton armPlaceButton = new JoystickButton(driver, Button.kB.value);

    
    private final Swerve s_Swerve = new Swerve();
    private final ArmSubsystem armSubsystem = new ArmSubsystem();

    private final IntakeCommand intakeSuck = new IntakeCommand(armSubsystem, -0.75);
    private final IntakeCommand intakeSpit = new IntakeCommand(armSubsystem, 0.95);

    // private final ArmManualCommand armManual = new ArmManualCommand(driver, armSubsystem, -0.8);
    // private final ArmManualCommand armDown = new ArmManualCommand(armSubsystem, 0.8);

   private final ArmPositionCommand armPickupPosition = new ArmPositionCommand(armSubsystem, -5);
   private final ArmPositionCommand armStorePosition = new ArmPositionCommand(armSubsystem, -3);
   private final ArmPositionCommand armMiddleConePlace = new ArmPositionCommand(armSubsystem, -4);


   private SendableChooser<Command> autoChooser = new SendableChooser<>();


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

        //arm manual command
        armSubsystem.setDefaultCommand(new ArmManualCommand(driver, armSubsystem, 0.35));

        // Configure the button bindings
        configureButtonBindings();
        armSubsystem.zeroAllEncoders();

        autoChooser.addOption("Nothing", null);

        autoChooser.addOption("Balance Auto", new balanceAuto(s_Swerve));

        autoChooser.addOption("Leave Community Auto", new leaveComZone(s_Swerve));

        autoChooser.addOption("Shoot One", new exampleAuto(s_Swerve, armSubsystem));

        SmartDashboard.putData(autoChooser);
    }

    
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        zeroArmEncoder.onTrue(new InstantCommand(() -> armSubsystem.zeroAllEncoders()));

        intakeSuckButton.whileTrue(intakeSuck);
        intakeSpitButton.whileTrue(intakeSpit);

        // armStoreButton.onTrue(armStorePosition);
        // armPickupButton.onTrue(armPickupPosition);
        // armPlaceButton.onTrue(armMiddleConePlace);
        
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // Assign your auto here
        // new InstantCommand(() -> s_Swerve.zeroGyro());
        return autoChooser.getSelected();
    }
}
