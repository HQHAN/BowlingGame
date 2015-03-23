package com.bowling.hans;

import java.util.Random;

import com.bowling.hans.ScoreManager.FrameScore;

public class TestGamePlay {
	public static void main(String[] args) {
		
		Random random = new Random();
		
		ScoreManager smForA = new ScoreManager();
		ScoreManager smForB = new ScoreManager();
		
		for(int i=0; i<10; i++) {
			FrameScore fs_A = smForA.createFrameScore();
			FrameScore fs_B = smForB.createFrameScore();
			
			if(i == 9) {
				
				// mark last element to handle it differently
				fs_A.setLastElement(true);
				fs_B.setLastElement(true);
				
				// roll first
				fs_A.setmFirst(String.valueOf(random.nextInt(11)));
				fs_B.setmFirst(String.valueOf(random.nextInt(11)));
				
				if(fs_A.isStrike()) {
					fs_A.setmSecond(String.valueOf(random.nextInt(11)));
				}else {
					// there are a few pins to down at second trial
					int randomPin = random.nextInt(11);
					
					while(fs_A.getmFirst() + randomPin > 10) {
						randomPin = random.nextInt(11);
					}
					
					fs_A.setmSecond(String.valueOf(randomPin));
				}
				
				if(fs_B.isStrike()) {
					fs_B.setmSecond(String.valueOf(random.nextInt(11)));
				} else {
					// there are a few pins to down at second trial
					int randomPin = random.nextInt(11);
					
					while(fs_B.getmFirst() + randomPin > 10) {
						randomPin = random.nextInt(11);
					}
					
					fs_B.setmSecond(String.valueOf(randomPin));
				}
				
				// if it is 10th frame , check if spare or strike occurs and then give it 3rd chance to roll
				if(fs_A.isStrike() || fs_A.isSpare()) 
					fs_A.setmThird(String.valueOf(random.nextInt(11)));
				
				if(fs_B.isStrike() || fs_B.isSpare())
					fs_B.setmThird(String.valueOf(random.nextInt(11)));
				
				
			} else {
				// 1. roll first
				fs_A.setmFirst(String.valueOf(random.nextInt(11)));
				fs_B.setmFirst(String.valueOf(random.nextInt(11)));
				
				// 2. if it is not strike then give one more chance to roll
				if(fs_A.isStrike() == false) {
					
					int randomPin = random.nextInt(11);
					
					// ignore invalid input value
					while(fs_A.getmFirst() + randomPin > 10) {
						randomPin = random.nextInt(11);
					}
					
					fs_A.setmSecond(String.valueOf(randomPin));
				}	
				
				if(fs_B.isStrike() == false) {
					int randomPin = random.nextInt(11);
					
					while(fs_B.getmFirst() + randomPin > 10) {
						randomPin = random.nextInt(11);
					}
					
					fs_B.setmSecond(String.valueOf(randomPin));
				}
					
			}
			
			
		}
		
		System.out.println("Player A score board !");
		smForA.displayScore();
		System.out.println();
		System.out.println("Player B score board !");
		smForB.displayScore();
		
	}

}
