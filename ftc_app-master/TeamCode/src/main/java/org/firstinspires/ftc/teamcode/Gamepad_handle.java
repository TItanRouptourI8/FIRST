package org.firstinspires.ftc.teamcode;

/*import android.os.AsyncTask;
import android.util.EventLog;
import android.view.KeyEvent;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;*/


public class Gamepad_handle extends WeRobotJ_mainV1_Linear {/*
    double initSortieO = 0.3;
    double initSortieB = 0.4;
    private HardwareWeRobotJ robot;
    private Gamepad gamepad1;
    public Gamepad_handle(HardwareWeRobotJ robot, Gamepad g) {
        this.robot = robot;
        this.gamepad1 = g;
    }
    AsyncTask<Void, Void, Void> myAsynctask = new AsyncTask<Void, Void, Void>() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            R1x = gamepad1.right_stick_x;
            R1y = -gamepad1.right_stick_y;
            L1y = gamepad1.left_stick_y;
            L1x = gamepad1.left_stick_x;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (WeRobotJ_mainV1_Linear.opModActif) {
                if (gamepad1.right_bumper) {
                    choixModeTri();
                }

                if ((R1y > Math.abs(R1x)) | (-R1y > Math.abs(R1x))) {
                    avance(convertPowerToCurve(R1y));
                } else {
                    tourne(convertPowerToCurve(R1x));
                }

                if (Math.abs(L1y) > 0.2) {
                    robot.Hanger.setPower(L1y / Math.abs(L1y) * 0.5);
                } else {
                    robot.Hanger.setPower(0);
                }

                if (Math.abs(L1x) > 0.2) {
                    rtPosCible += L1x / Math.abs(L1x) * 96;
                    robot.roueTri.setTargetPosition(rtPosCible);
                    robot.waitForTick(100);
                }

                if (gamepad1.y) {
                    robot.sortieOranges.setPosition(initSortieO + .25);
                    robot.waitForTick(500);
                    robot.sortieOranges.setPosition(initSortieO);
                }

                if (gamepad1.x) {
                    robot.sortieBleues.setPosition(initSortieB - .25);
                    robot.waitForTick(500);
                    robot.sortieBleues.setPosition(initSortieB);
                }
            }
            return null;
        }
    };
    /*returnH listener;
    @Override
    protected boolean pressed(KeyEvent event) {
        listener.returnvalue(event);
        return super.pressed(event);
    }
    public void setListener(returnH h)
    {
        h = listener;
    }

    interface returnH
    {
        void returnvalue(KeyEvent e);
    }*/


}
