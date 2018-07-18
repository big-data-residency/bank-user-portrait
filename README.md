# bank-user-portrait
git操作方式：
	
	将本地数据更新和远端同步：git pull
	
	查看本地和远端所有分支：git branch -a
	
	创建本地***分支和远端***分支同步：git checkout -b *** origin/***
	
	我习惯的更新代码方式：
		1.在本地切换到dev分支：git checkout dev
		2.将本地dev分支更新和远端dev分支同步：git pull origin
		3.在本地切换到自己的分支***：git checkout ***
		4.将本地dev分支合并到自己的分支：git merge dev
		5.上步之后往往会有冲突，在本地解决冲突并提交：git add .       git commit -m "解决冲突"
		6.将自己本地的分支推到远端：git push origin
		7.在远端提交pull request
