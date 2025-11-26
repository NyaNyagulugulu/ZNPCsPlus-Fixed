# ZNPCsPlus-Fixed
这是基于ZNPCsPlus的修复版本，已知原版问题

### 1. 指令中无法携带空格的问题
在原版中，执行以下命令时：
```
/npcs action edit 排行榜助手 0 playercommand ANY_CLICK 1 0 pit leaderboard
```
命令 `pit leaderboard` 中的空格无法被正确识别。

**修复方式：** 现在支持使用引号包含包含空格的命令：
```
/npcs action edit 排行榜助手 0 playercommand ANY_CLICK 1 0 "pit leaderboard"
```

### 2. playercommand执行身份问题
部分指令使用playercommand参数执行时无法使用玩家的身份执行。

**修复方式：** 确保playercommand使用玩家身份执行指令，而不是模拟或使用控制台身份。
```
/npcs action edit <npc名称> <动作索引> playercommand <点击类型> <冷却时间> <延迟> <命令>
```

## 使用示例
```
# 为NPC添加玩家命令动作，当玩家点击时执行pit leaderboard命令
/npcs action edit 排行榜助手 0 playercommand ANY_CLICK 1 0 "pit leaderboard"

# 使用玩家身份执行tp命令
/npcs action edit testnpc 0 playercommand LEFT_CLICK 0 0 "tp @p 0 100 0"
```

## 功能说明
- playercommand: 以玩家身份执行命令
- consolecommand: 以控制台身份执行命令
- playerchat: 让玩家发送消息
- message: 向玩家发送消息