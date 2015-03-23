package com.bowling.hans;

import java.io.IOException;
import java.util.Scanner;

import com.bowling.hans.ScoreManager.FrameScore;

public class BowlingGame {
	
	static ScoreManager mScoreManager;
	
	public static void main(String[] args) throws IOException {
		
		mScoreManager = new ScoreManager();
		
		// 1. get the no of Pin per each frame from the console
		Scanner sc = new Scanner(System.in);
		
		int i = 0;
		while(i < 10) {
			
			FrameScore fs = mScoreManager.createFrameScore();
			
			if(fs == null)
				break;
			
			// take care of 10th frame specially
			if(i == 9) {
				String pin = getPin(sc);
				
				fs.setmFirst(pin);
				fs.setmSecond(getPin(sc));
				// if spare or strike occurs , then give a third chance to roll
				if(fs.isStrike() || fs.isSpare() )
					fs.setmThird(getPin(sc));
				
				// mark last element to take special care of when displaying last frame information
				fs.setLastElement(true);
			
			} else {
				
				String pin = getPin(sc);
				
				if(pin.equals("10")) {
					// if strike , get input once and then go to next frame
					fs.setmFirst(pin);
				}else{
					// if not strike, then get input twice
					fs.setmFirst(pin);
					fs.setmSecond(getPin(sc));
				}
			}
			
			// print the result 
			mScoreManager.displayScore();
			
			i++;
		}
		
		mScoreManager.clearScore();
		
	}
	
	private static String getPin(Scanner sc) {
		String ret; // since 'F' is allowed to put for foul case, get the input as String
		System.out.println(mScoreManager.getCurrentFrameNum() + " Frame 핀수 : ");
		ret = sc.next();
		
		return ret.equalsIgnoreCase("F") ? "-1" : ret;
	}	

}
