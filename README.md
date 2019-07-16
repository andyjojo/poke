# poke
扑克残局暴力破解程序。

玩家A：
"梅花3", "方块4", "梅花4", "方块5", "黑桃5", "黑桃6", "红桃6", "红桃7", "方块7", 
"红桃9", "梅花9", "方块Q", "梅花Q", "红桃Q", "梅花K", "方块K", "黑桃K", "梅花2"
![Player A](https://github.com/anguo-wenz/poke/raw/master/PlayerA-sn.jpg)

玩家B：
"红桃A", "方块A", "红桃J", "方块J"
![Player B](https://github.com/anguo-wenz/poke/raw/master/PlayerB-sn.jpg)

规则如下：
1. 2最大，3最下
2. 可出单张，一对，三条带一对，同花五张，顺子五张
3. 不可出连队
4. 牌多者即玩家A先出

本程序使用暴力破解，结论是玩家A必胜。

下载后使用IDE打开，然后运行ForcePlay.java

玩家A先出黑桃6或红桃6， 然后B有三种应法，出个J，出个A，或不出

# B出J，那么A出红桃Q，
1. B出A压住，然后A就出2，后面出对9，再对K，再出顺子3，4，5，6，7，再出方块同花4，5，7，Q，K，最后出梅花Q
2. B如果不出，那么B还剩J，A，A，而A有单张3，6，2，其他都是对以及三个K，那就出3
    - 如果B出J，A就出K压住。
        - B再出A，A就出2，B还剩A，A都成对，除了一张6。先出对，最后出6即可。
        - B如果不出，A就出单张6，后面A都成对以及一个2，B有对A，A就一直拆对单张出，然后时候B如果出A压住，A就出2压住。然后把单张最后出即可。
    - 如果B出A，A就出2压住。此时B还有一个A，一个J。而A除了一个6，其他都是对以及三个K，最后出6即可。
    - 如果B不出。那就同样，先出6，再拆对一个一个出。

# B出A，那么A就出2。B还有对J，一个A，此时A再出对9，
1. B出对J，A出红桃Q 梅花Q。然后出同花457QK，再出顺子34567，最后对K
2. B不出，A还是出红桃Q 梅花Q。然后出同花457QK，再出顺子34567，最后对K

# B只能不出。A就再出另一张6
1. B出J，那么A出红桃Q，
    - B出A压住，然后A就出2，后面出所有对子，然后三张K，最后出3
    - B如果不出，那么B还剩J，A，A，而A有单张3，2，其他都是对以及三个K，那就出3
        - 如果B出J，A就出梅花Q压住。此后A有单张Q，2，B有对A
            - B再出A，A就出2，B还剩A，A都成对，除了一张Q。先出对，最后出6即可。
            - B如果不出，A就出单张Q，后面A都成对以及一个2，B有对A，A就一直拆对单张出，然后时候B如果出A压住，A就出2压住。然后把单张最后出即可。
        - 如果B出A，A就出2压住。此时B还有一个A，一个J。而A都是对以及三个K，出对出三张即可。
        - 如果B不出。那就同样，先出6，再拆对一个一个出
2. B出A，那么注意，此时A要Pass一次。不能出2。B还有对J，一个A，
    - B出对J，A出红桃Q 方块Q。然后出对4，对5，对7，梅花同花39QK2，再出对K，最后一张红桃9
    - B出一个J，A出红桃Q，而A有单张3，2，其他都是对以及三个K，那就出3，然后拆对一张一张出即可。
    - B出一个A，A出2压住后，B剩对J，A只有单张3，还有三个Q，三个K，出3再拆对一张一张出即可。任何时候如果B出J，就用Q或K压住，然后出对，有单张有最后即可。
3. B还是不能出，此时A再出3
 to be continue

有些变化的三张不可以带一对。如果三张不可以带一对，那么多牌无法取胜。

# solution-in-all.txt 
包含所有应对之策。就是在任何情况，A出任何牌或不出，都能找到其中一行对应。
（注意玩家B的牌被改变成两个红桃A，两个红桃J，因为B的牌花色不影响出牌变化）。 </br>
比如Line 1: </br>
|A:[♥6]|B:[♥J]|A:[♥Q]|B:[♥A]|A:[♣2]|B:PASS|A:[♥9, ♣9]|B:PASS|A:[♠K, ♣K]|B:PASS|A:[♣3, ♣4, ♠5, ♠6, ♥7]|B:PASS|A:[♦4, ♦5, ♦7, ♦Q, ♦K]|B:PASS|A:[♣Q] </br>
这一行说明
玩家A显出红桃6，然后B出J，然后A出红桃Q，然后B出A，这时候A出2压住后，B还剩一个A一个J，然后B出对9，再对K，再出顺子34567，再出方块同花457QK，最后出梅花Q就出完了。</br>
再比如 Line 266: </br>
|A:[♥6]|B:P|A:[♠6]|B:[♥A]|A:P|B:[♥J, ♥J]|A:[♥Q, ♦Q]|B:P|A:[♣4, ♦4]|B:P|A:[♠5, ♦5]|B:P|A:[♥7, ♦7]|B:P|A:[♠K, ♦K]|B:P|A:[♣3, ♣9, ♣Q, ♣K, ♣2]|B:P|A:[♥9] </br>
A先出6，B Pass, 然后A再出6后B用A压住，此时A就要pass一次。</br>
再比如Line 666: </br>
|A:[♥6]|B:P|A:[♠6]|B:P|A:[♣3]|B:P|A:[♦4]|B:P|A:[♣4]|B:P|A:[♠5]|B:P|A:[♦5]|B:P|A:[♥7]|B:[♥A]|A:[♣2]|B:P|A:[♥Q, ♣Q, ♦Q]|B:P|A:[♥9, ♣9, ♠K, ♣K, ♦K]|B:P|A:[♦7] </br>
意味着A先出6634455后B都pass，然后A出一张7时，B出A，这时候A就用2压住，然后3张Q，再三张带一对，最后出7结束。</br>
