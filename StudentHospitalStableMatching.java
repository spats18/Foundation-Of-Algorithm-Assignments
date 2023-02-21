import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue; 
import java.util.Scanner;
import java.util.Set;

public class GS_StudentHospital {
    public static void main(String[] args){
        System.out.println("***************************************");
        System.out.println("Assignment 2: Q4");
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of students");
        int students = sc.nextInt();

        System.out.println("Enter the number of hospitals");
        int hospitals = sc.nextInt();
        
        int[][] stuPref = new int[students][hospitals];
        for(int i = 0; i<students; i++){
            System.out.println("Enter the preference of hospitals for student "+ (i+1));
            for(int j=0; j<hospitals; j++)
                stuPref[i][j] = sc.nextInt() - 1;
        }

        int[][] hosPref = new int[hospitals][students];
        for(int i = 0; i<hospitals; i++){
            System.out.println("Enter the preference of students for hospital "+ (i+1));
            for(int j=0; j<students; j++)
                hosPref[i][j] = sc.nextInt() - 1;
        }

        int[] req = new int[hospitals];
        for(int i=0; i<hospitals; i++){
            System.out.println("Enter the requirement for hospital "+ (i+1));
            req[i] = sc.nextInt();
        }

        Set<Integer>[] match = getMatchings(stuPref, hosPref, req);
        System.out.println("Stable matchings are as follows");
        for(int i=0; i<hospitals; i++){
            System.out.print("h" + (i+1)+": ");
            for(int mat : match[i])
                System.out.print("s" + (mat+1)+ " ");
            System.out.println();
        }
        System.out.println("***************************************");
    }

    public static Set<Integer>[] getMatchings(int[][] stuPref, int[][] hosPref, int[] req){
        int students = stuPref.length;
        int hospitals = hosPref.length;
        //prefNumHospAt to keep track of proposals made by hospitals
        int[] prefNumHospAt = new int[hospitals];
        //studMatch to keep track of which student is committed to which hospital
        int[] studMatch = new int[students];
        //keeping track of unmatched hospitals and students
        Queue<Integer> freeHosp = new LinkedList<>();
        Set<Integer> freeStud = new HashSet<>();
        for(int i=0; i< hospitals; i++)
            freeHosp.offer(i);
        for(int i=0; i< students; i++)
            freeStud.add(i);
        
        Set<Integer>[] match = new HashSet[hospitals];
        inversePref(stuPref);

        while(!freeHosp.isEmpty()){
            int currentHosp = freeHosp.peek();
            int studNum = prefNumHospAt[currentHosp];
            int proposeStu = hosPref[currentHosp][studNum];
            if(studNum==0)
                match[currentHosp] = new HashSet<>();
            prefNumHospAt[currentHosp]++;

            if(freeStud.contains(proposeStu)){
                req[currentHosp]--;
                freeStud.remove(proposeStu);
                match[currentHosp].add(proposeStu);
                studMatch[proposeStu] = currentHosp;
            }
            else{
                int previousHosp = studMatch[proposeStu];
                if(stuPref[proposeStu][previousHosp] > stuPref[proposeStu][currentHosp]){
                    req[previousHosp]++;
                    match[previousHosp].remove(proposeStu);
                    if(req[previousHosp]==1)
                        freeHosp.offer(previousHosp);
                    req[currentHosp]--;
                    match[currentHosp].add(proposeStu);
                    studMatch[proposeStu] = currentHosp;
                }
            }
            if(req[currentHosp]==0)
                freeHosp.poll();
        }
        return match;
    }
    public static void inversePref(int[][] stuPref){
        //inverse the student preferences for constant time access.
        int hosp = stuPref[0].length;
        int[] helper = new int[hosp];
        for(int i=0; i<stuPref.length; i++){
            for(int j = 0; j<hosp; j++)
                helper[j] = stuPref[i][j];
            for(int j = 0; j<hosp; j++)
                stuPref[i][helper[j]] = j;
        }
    }
}
