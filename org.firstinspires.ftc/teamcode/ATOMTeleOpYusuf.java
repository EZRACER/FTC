/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.ATOM;

import org.firstinspires.ftc.teamcode.ATOM.*;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name="ATOMTeleOp Java", group="Linear Opmode")

public class ATOMTeleOp extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor LeftDriveRear = null;
    private DcMotor RightDriveRear = null;
    private ATOMHardware robot   = new ATOMHardware();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeMotors();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;
            float clawPosition;

            // Make a Square
            // leftPower  = 0.3;
            // rightPower = 0.3 ;
           
             telemetry.addData("Calling", "DriveTrain");
             telemetry.update();
             ATOMDriveTrain ATOMDriveTrain = new ATOMDriveTrain(robot) ;
             
             
            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            //double drive = -gamepad1.left_stick_y;
            //double turn  =  gamepad1.right_stick_x;
            //leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            //rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
            
             // Tank Mode uses one stick to control each wheel.
             // - This requires no math, but it is hard to drive forward slowly and keep straight.
                  leftPower  = -gamepad1.left_stick_y ;
                  rightPower = -gamepad1.right_stick_y ;
                  clawPosition = gamepad1.right_trigger;
                  
                  robot.leftClaw.setPosition(clawPosition);
                  robot.rightClaw.setPosition(clawPosition);
                  
                  if( gamepad1.right_bumper) {
                   // robot.liftArm.setPower(.1);
                  }else {
                   // robot.liftArm.setPower(.1);
                  }
             
             
             ATOMDriveTrain.PDrive(leftPower,rightPower,0.0); // Move Forward for 5 Seconds
             //DriveTrain.EDrive(leftPower,24,24,5,90); // Move Forward for 5 Seconds
          
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
        
          
    }
    
     public void InitializeMotors() {
        
        robot.init(hardwareMap);
        //LeftDriveRear  = hardwareMap.get(DcMotor.class, "LeftDriveRear");
        //RightDriveRear = hardwareMap.get(DcMotor.class, "RightDriveRear");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        LeftDriveRear.setDirection(DcMotor.Direction.FORWARD);
        RightDriveRear.setDirection(DcMotor.Direction.REVERSE);
        LeftClaw.setDirection(DcMotor.Direction.REVERSE);        
         }
     public void Drive(double Left, double Right,double timeoutS) {
            runtime.reset();
            while (runtime.seconds() < timeoutS) {
                 // runtime.reset();
                LeftDriveRear.setPower(Left);
                RightDriveRear.setPower(Right);
                
               }
               // Set Power to Zero
                LeftDriveRear.setPower(0);
                RightDriveRear.setPower(0);
        
         }
           
}
   
    
