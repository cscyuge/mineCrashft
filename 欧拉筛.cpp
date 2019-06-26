/*求小于等于n的素数的个数*/
#include<stdio.h>
#include<string.h>
using namespace std;
int main()
{
    int n, cnt = 0;
    int prime[100001];//存素数 
    bool vis[100001];//保证不做素数的倍数 
    scanf("%d", &n);
    memset(vis, false, sizeof(vis));//初始化 
    memset(prime, 0, sizeof(prime));
    for(int i = 2; i <= n; i++)
    {
        if(!vis[i])//不是目前找到的素数的倍数 
        prime[cnt++] = i;//找到素数~ 
        for(int j = 0; j<cnt && i*prime[j]<=n; j++)
        {
            vis[i*prime[j]] = true;//找到的素数的倍数不访问 
            if(i % prime[j] == 0) break;//关键！！！！ 
        }
    }
    printf("%d\n", cnt);
    return 0;
}
