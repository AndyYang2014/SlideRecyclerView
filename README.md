# SlideRecyclerView

仿QQ消息列表，侧滑删除效果


[点击下载Demo](https://github.com/MyLucifer/SlideRecyclerView/raw/master/app/release/app-release.apk "demo下载请点击")

##使用教程
###①首先添加依赖

		//Add it in your root build.gradle at the end of repositories:
    	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

		//Add the dependency
    	dependencies {
	        compile 'com.github.MyLucifer:SlideRecyclerView:1.0.1'
	}


###②Adapter代码示例
    public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private Context mContext;

    public MainListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		//主布局
        View content = LayoutInflater.from(mContext).inflate(R.layout.item_content, null);
		//菜单布局，都可以自定义
        View menu = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
		//将两个布局添加至SlideItem中
        SlideItem slideItem = new SlideItem(mContext);
        slideItem.setContentView(content, menu);
        return new ViewHolder(slideItem);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.view.setTag(position);

        holder.menuView.findViewById(R.id.item_totop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了置顶" + position, Toast.LENGTH_LONG).show();
            }
        });

        holder.menuView.findViewById(R.id.item_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了删除" + position, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public View contentView;
        public View menuView;

        ViewHolder(SlideItem view) {
            super(view);
            this.view = view;
            contentView = view.getContentView();
            menuView = view.getMenuView();

        }
    }
####③完工，但是有个已知问题，当content或者menu的根布局为LinearLayout时无法滑动。

####④如遇见问题请提Issues或者发送邮件至我邮箱。


### 个人博客站    [http://www.andyyang2014.com](http://www.andyyang2014.com "个人博客站")

### 个人邮箱  AndyYang2014@126.com




