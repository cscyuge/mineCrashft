/*��С�ڵ���n�������ĸ���*/
#include<stdio.h>
#include<string.h>
using namespace std;
int main()
{
    int n, cnt = 0;
    int prime[100001];//������ 
    bool vis[100001];//��֤���������ı��� 
    scanf("%d", &n);
    memset(vis, false, sizeof(vis));//��ʼ�� 
    memset(prime, 0, sizeof(prime));
    for(int i = 2; i <= n; i++)
    {
        if(!vis[i])//����Ŀǰ�ҵ��������ı��� 
        prime[cnt++] = i;//�ҵ�����~ 
        for(int j = 0; j<cnt && i*prime[j]<=n; j++)
        {
            vis[i*prime[j]] = true;//�ҵ��������ı��������� 
            if(i % prime[j] == 0) break;//�ؼ��������� 
        }
    }
    printf("%d\n", cnt);
    return 0;
}
