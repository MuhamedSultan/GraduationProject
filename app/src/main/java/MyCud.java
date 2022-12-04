class MyCud :
        RecyclerView.Adapter<MyCud.MyViewuy>{
private var mcontext:Context
private  var mylist:List<User>

    constructor(context:Context,mylist:List<User>){
        this.mcontext=context
        this.mylist=mylist
        }

        override fun onCreateMyViewuy(p0:ViewGroup,p1:Int):{

        val v=LayoutInflater.from(p0.context).inflate(R.layout.card,p0,false)
        return MyViewuy(v)
        }

        override fun getItemCount():Int{

        return mylist.size
        }

        override fun onBindMyViewuy(p0:MyViewuy,p1:Int){
        var infUser=mylist[p1]

        }



 class MyViewuy(itemView:View) :RecyclerView.ViewHolder(itemView){


        }
        }