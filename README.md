# SpiderWeb
### 简介：
此库是本人用来实验怎样把本地的项目制作成maven仓库，库本身是一个蜘蛛网展示各个属性值，效果如下图：
![效果图](https://github.com/zfisll/SpiderWeb/blob/master/app/src/main/res/mipmap-hdpi/1509438664809.jpg)

### 该自定义控件自定义了部分属性：

- count ：表示层数，比如5，就会绘制5个图形，依次从小到大
- polygon ：多边形边数，比如6，表示正6变形
- pointColor ：属性点颜色
- dataColor ：覆盖层颜色

### 对外提供的方法：
- setRate(float[] rates)，设置每条边属性的比重，跟多边形数量对应
- setCount(int count)
- setPolygon(int polygon)

### Gradle依赖：
工程目录下build.gradle中添加：
 ```
 allprojects {
    repositories {
        google()
        jcenter()
        maven {url 'https://jitpack.io'}
    }
}
 ```
module目录下build.gradle添加：

```
compile 'com.github.zfisll:SpiderWeb:1.0.1'
```

### 提示：该库是本人用来实验的一个库，库本身功能实现了，但代码比较粗糙，如果有心，可以基于本库来优化自己的代码
