package ucsdm3hw2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class StronglyConnected {
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
        int[] numOfScc = new int[adj.length];       //indicate this vertex is in which SCC;
//        int inDegree[] = new int[adj.length];           // number of in-degrees of a vertex;
        int sccs = 0;        // TOTAL number of SCCs;
//        StackOfInts stack0 = new StackOfInts(adj.length);    // stack0 stores those vertices whose in-degree is 0;   
//        for (int i = 0; i < adj.length; i++)
//        {
//            for (int j : adj[i])
//                inDegree[j]++;           // increase the times this vertex is visited by 1;
//        }
//        for (int i = 0; i < adj.length; i++)
//            if (inDegree[i] == 0)
//                stack0.push(i);
        int cur = 0;
        outer: while (cur < adj.length)
        {
            while(cur < adj.length && numOfScc[cur] != 0)
                cur++;
            if (cur == adj.length) break outer;
            int v = cur;
            numOfScc[v] = ++sccs;
            StackOfInts stack1 = new StackOfInts(adj.length);
            stack1.push(v);
//            System.out.println("sccs : " + sccs);         // for_test
//            for (int i = 0; i < inDegree.length; i++)
//                System.out.println("in-degree of i " + inDegree[i]);
            ArrayList<Integer> store = new ArrayList<Integer>();
            int flag = 0;        //shows whether this is a SCC(which has size greater than 1);
            loop: while (!stack1.isEmpty())            //Depth first search;
            {
                int o = stack1.top();
                for (int j : adj[o])
                {
                    if (j == v)
                        flag = 1;
                    if (numOfScc[j] == 0)
                    {
                        numOfScc[j] = sccs;
                        stack1.push(j);
                        continue loop;
                    }
                }
                store.add(stack1.pop());
            }
            if (flag == 0)          // only v itself forms a SCC;
            {
//                System.out.println("啪啪啪");      //test
                while (!store.isEmpty())
                {
                    int unvisited = store.remove(0);
                    numOfScc[unvisited] = 0;
                }
                numOfScc[v] = sccs;
//                for (int vNext : adj[v])
//                {
//                    inDegree[vNext]--;
//                    System.out.println("in-degree of v-next: " + inDegree[vNext]);
//                    if (inDegree[vNext] == 0)
//                        stack0.push(vNext);
//                }

                adj[v].clear();         // clear all of v's out-edge;
            } else {
//                System.out.println("啪啪啪");      //test
                while (!store.isEmpty())
                {
                    int visited = store.remove(0);
//                    for (int vNext : adj[visited])
//                    {
//                        inDegree[vNext]--;
//                    }
                    adj[visited].clear();         // clear all of v's out-edge;
                }
            }
                for (int i = 0; i < numOfScc.length; i++)
                    System.out.println("scc: " + numOfScc[i]);        //for _test;

        }

            
        return sccs;
    }
        private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        boolean[] visited = new boolean[adj.length];          //initialize visited[n]; ---> all equal to 0;
        StackOfInts stack = new StackOfInts(adj.length);
        visited[x] = true;
        stack.push(x);
        loop : while(!stack.isEmpty())
        {
            int o = stack.top();
            if (o == y) return 1;
            for (int j : adj[o])
            {
                if (visited[j] == false)
                {
                    visited[j] = true;
                    stack.push(j);
                    continue loop;
                }
            }
            // else, pop o out;
            stack.pop();
        }
        return 0;
    }

    private static class StackOfInts
    {
        private int[] a;
        private int N;
        public StackOfInts(int cap) 
        {
            a = new int[cap];
            N = 0;
        }
        public boolean isEmpty() { return N == 0;}
        public int size() { return N; }
        public void push(int item) { a[N++] = item; }
        public int pop() { return a[--N]; }
        public int top() { 
                return a[N - 1];      //No consideration of null exception(have ruled it out in "reach" function;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}

