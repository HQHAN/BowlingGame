package com.bowling.hans;

import java.util.ArrayList;

public class ScoreManager {
	
	ArrayList<FrameScore> mScores;
	
	public ScoreManager() {
		mScores = new ArrayList<FrameScore>();
	}
	
	public FrameScore createFrameScore() {
		if(mScores.size() == 10) // don't allow the FrameScore count to reaches more than 11
			return null;
		
		FrameScore score = new FrameScore();
		mScores.add(score);
		
		return score;
	}
	
	public void clearScore() {
		mScores.clear();
	}
	
	// This is for displaying current frame number in console
	public int getCurrentFrameNum() {
		return mScores.size();
	}
	
	// note : frame is zero based index
	public FrameScore getFrameScore(int frame) {
		if(mScores.isEmpty()) 
			return null;
		
		if(mScores.size() <= frame)
			return null;
		
		return mScores.get(frame);
	}
	
	public void displayScore() {
		System.out.println("------------------------------------------");
		System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10  |");
		System.out.println("------------------------------------------");
		
		for(int i=0; i<mScores.size(); i++) {
			FrameScore fs = mScores.get(i);
			if(fs.lastElem) {
				// print first trial score
				if(fs.getmFirst() == 10)
					System.out.print("|"+"X");
				else
					System.out.print("|"+getSymbolOrScore(fs.getmFirst()));
				
				// second
				if(fs.getmSecond() == 10)
					System.out.print("|"+"X");
				else if(fs.isSpare())
					System.out.print("|"+"/");
				else
					System.out.print("|"+getSymbolOrScore(fs.getmSecond()));
				
				// third 
				// According to the rule, if fist trial is strike then give 2 more chances
				// If second trial makes spare or strike , then give third chance
				if(fs.isStrike() || fs.isSpare() || fs.getmSecond() == 10)
					System.out.print("|"+getSymbolOrScore(fs.getmThird()));
				
			}else {
				if (fs.isStrike()) {
					System.out.print("|"+"X"+"|"+" ");
				} else if (fs.isSpare()) {
					System.out.print("|"+getSymbolOrScore(fs.getmFirst()) +"|"+"/");
				} else {
					System.out.print("|"+getSymbolOrScore(fs.getmFirst())+"|"+getSymbolOrScore(fs.getmSecond()));
				}
			}
		}
		
		System.out.println("|");
		System.out.println("-------------------------------------------");
		
		for(int i=1; i<mScores.size() + 1; i++) {
			int totalScore = getTotalScore(i);
			System.out.print("| "+ ( totalScore == -1 ? " " : totalScore ) +" ");
		}
		System.out.println("|");
		System.out.println("-------------------------------------------");
		
	}

	private String getSymbolOrScore(int fs) {
		String ret;
		if(fs == 0)
			ret = "-";
		else if(fs == -1)
			ret = "F";
		else 
			ret = String.valueOf(fs);
		
		return ret;
	}
	
	public int getTotalScore(int currentFrameNum) {
		
		int totalScore = 0;
		for(int i=0; i<currentFrameNum; i++) {
			FrameScore fs = mScores.get(i);
			
			if(fs.lastElem) {
				int first = fs.getmFirst() == -1 ? 0 : fs.getmFirst();
				int second = fs.getmSecond() == -1 ? 0 : fs.getmSecond();
				int third = fs.getmThird() == -1 ? 0 : fs.getmThird();
				
				totalScore += first + second + third;
				
			} else {
				if(fs.isStrike()) {
					int nextPoint = getNextTwoPinsDownForStrike(i); 
					if( nextPoint == -1) 
						return -1;
					
					totalScore += 10 + nextPoint;
					
				}else if(fs.isSpare()) {
					int nextPoint = getNextPinDownForSpare(i);
					if( nextPoint == -1) 
						return -1;
					
					totalScore += 10 + nextPoint;
					
				}else {
					int first = fs.getmFirst() == -1 ? 0 : fs.getmFirst();
					int second = fs.getmSecond() == -1 ? 0 : fs.getmSecond();
					
					totalScore += first + second;
				}
			}
			
		}
		return totalScore;
	}
	
	private int getNextTwoPinsDownForStrike(int currentFrameNum) {
		// if requested frame is last then there is no next frame so returning -1
		// to indicate there is no available next frame score
		if(mScores.size() - 1 == currentFrameNum)
			return -1;
		
		FrameScore nextFrame = mScores.get(currentFrameNum+1); // get the next frame score
		
		int first = nextFrame.getmFirst() == -1 ? 0 : nextFrame.getmFirst();
		int second = nextFrame.getmSecond() == -1 ? 0 : nextFrame.getmSecond();
		
		int ret = first + second;
		
		return ret;
	}
	
	private int getNextPinDownForSpare(int currentFrameNum) {
		// if requested frame is last then there is no next frame so returning -1
		// to indicate there is no available next frame score
		if(mScores.size() - 1 == currentFrameNum)
			return -1;
		
		FrameScore nextFrame = mScores.get(currentFrameNum+1); // get the next frame score
		
		int ret = nextFrame.getmFirst() == -1 ? 0 : nextFrame.getmFirst();
		
		return ret;
	}
	
	class FrameScore {
		
		// mFirst == 10 means strike
		// mFirst + mSecond == 10 means spare
		// 0 means gutter
		// -1 means foul
		int mFirst;
		int mSecond;
		int mThird;
		
		boolean lastElem; // indicate the this is the last FrameScore(10th frame)
		
		public FrameScore() {
			mFirst = -1;
			mSecond = -1;
			mThird = -1;
			
			lastElem = false;
		}
				
		public void setLastElement(boolean b) {
			lastElem = b;
		}
		
		public int getmFirst() {
			return mFirst;
		}

		public void setmFirst(String mFirst) {
			
			this.mFirst = Integer.valueOf(mFirst);
		}
		
		public int getmSecond() {
			return mSecond;
		}

		public void setmSecond(String mSecond) {
			
			this.mSecond = Integer.valueOf(mSecond);
		}
		
		public int getmThird() {
			return mThird;
		}
		
		public void setmThird(String mThird) {
			this.mThird = Integer.valueOf(mThird);
		}
		
		public boolean isStrike() {
			if(mFirst == 10) 
				return true;
			else
				return false;
		}
		
		public boolean isSpare() {
			
			if(isStrike()) return false;
			
			if(mFirst + mSecond == 10) 
				return true;
			else
				return false;
		}
	}
}
