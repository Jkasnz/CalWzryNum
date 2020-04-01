import java.util.Scanner;

public class calWzryNum {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入你的大段位（青铜/白银/黄金/白金/钻石/星耀）\n小段位（数字）,星星数（数字）\n以回车隔开：");
		String dDuanWei=sc.nextLine();
		int xDuanWei=sc.nextInt();
		int xxNum=sc.nextInt();

		calNum cn=new calNum(dDuanWei,xDuanWei,xxNum);

		System.out.println("请输入你的胜率（小数）：");
		float sl =sc.nextFloat();

		cal c=new cal(cn.num,sl);
		System.out.println(c.cs);
		System.out.println(c.sb);


	}
}

//根据段位计算还要多少颗星才能上王者
class calNum{
	int num=0;
	public calNum(String dDuanWei,int xDuanWei,int xxNum) {
		switch (dDuanWei) {
		case "青铜":
			num=104+(xDuanWei-1)*3+(3-xxNum);
			break;
		case "白银":
			num=92+(xDuanWei-1)*3+(4-xxNum);
			break;
		case "黄金":
			num=76+(xDuanWei-1)*4+(4-xxNum);
			break;
		case "白金":
			num=51+(xDuanWei-1)*5+(5-xxNum);
			break;
		case "钻石":
			num=26+(xDuanWei-1)*5+(5-xxNum);
			break;
		case "星耀":
			num=1+(xDuanWei-1)*5+(5-xxNum);
			break;
		}

	}
}

//胜率模拟
class cal{
	int cs=0;
	int jifen=0;
	int ls=0;

	boolean flag;
	StringBuilder sb=new StringBuilder();

	public cal(int num, float winRate) {

		while(num>0) {
			double rnd = Math.random();
			if(rnd<winRate){
				num--;
				cs++;
				ls++;
				flag=true;
				if(jifen>=totalJf(num)) {
					num--;
					jifen=jifen-totalJf(num);
				}
				jifen=jifen+3+jyp(flag,winRate)+mc(winRate)+sljq(winRate)+lsAdd(ls);
				sb.append("胜,");
			}else {
				if(jifen<saveJf(num)) {
					num++;
				}else {
					jifen=jifen-saveJf(num);
				}
				cs++;
				ls=0;
				flag=false;
				jifen=jifen+3+jyp(flag,winRate)+mc(winRate)+dsgq(winRate);
				sb.append("败,");
			}

		}
		sb.deleteCharAt(sb.length()-1);
	}

	//金银牌判断
	public static int jyp(boolean flag,float winRate){
		double jyp=0.2+(winRate-0.5);
		double rnd = Math.random();
		double jp=jyp*0.3,
				yp=jyp-jp;

		if(flag&&rnd<jp) {
			return 50;
		}else if(flag&&rnd>jp&&rnd<jyp){
			return 25;
		}else if(!flag && rnd<jp*0.15) {
			return 50;
		}else if(!flag && rnd>jp*0.15 && rnd<jyp/2.5) {
			return 25;
		}else {
			return 0;
		}
	}

	//名次 判断
	public static int mc(float winRate){
		double rnd = Math.random();
		double first=0.2+0.2*winRate;
		double second=first+0.2+0.15*winRate;
		double third=second+0.2;
		double fourth=third+0.2-0.15*winRate;

		if(rnd<=first) {
			return 5;
		}else if(rnd>first&&rnd<=second) {
			return 4;
		}else if(rnd>second&&rnd<=third) {
			return 3;
		}else if(rnd>third&&rnd<=fourth) {
			return 2;
		}else{
			return 1;
		}
	}

	//实力较强加分
	public static int sljq(float winRate) {
		double rnd1 = Math.random();
		double rnd2 = Math.random();
		if(rnd1<0.6+(winRate-0.5)*0.3) {
			return (int) (50*rnd2+winRate*0.03);
		}else {
			return 0;
		}
	}

	//对手过强加分
	public static int dsgq(float winRate) {
		double rnd1 = Math.random();
		double rnd2 = Math.random();
		if(rnd1<0.4+(winRate-0.5)*0.3) {
			return (int) (50*rnd2+winRate*0.03);
		}else {
			return 0;
		}
	}

	//连胜加分
	public static int lsAdd(int ls) {
		switch(ls) {
		case 2:
			return 5;
		case 3:
			return 10;
		case 4:
			return 15;
		case 0:
			return 0;
		case 1:
			return 0;
		default:
			return 20;
		}
	}

	//总积分
	public static int totalJf(int num) {
		if(num>=104 || (num<104&&num>=92)) {
			return 180;
		}else if(num<92&&num>=76) {
			return 240;
		}else if(num<76&&num>=51) {
			return 600;
		}else {
			return 1500;
		}

	}

	//保护积分
	public static int saveJf(int num) {
		if(num>=104 || (num<104&&num>=92)) {
			return 60;
		}else if(num<92&&num>=76) {
			return 80;
		}else if(num<76&&num>=51) {
			return 150;
		}else {
			return 300;
		}

	}
}



