.class Lolive/app/OliveArtists$1;
.super Ljava/lang/Object;
.source "OliveArtists.java"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lolive/app/OliveArtists;->onArtistsShown(Landroid/app/Activity;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$adapter:Lolive/app/OliveArtists$Adapter;

.field final synthetic val$tab:Landroid/app/Activity;


# direct methods
.method constructor <init>(Landroid/app/Activity;Lolive/app/OliveArtists$Adapter;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 114
    iput-object p1, p0, Lolive/app/OliveArtists$1;->val$tab:Landroid/app/Activity;

    iput-object p2, p0, Lolive/app/OliveArtists$1;->val$adapter:Lolive/app/OliveArtists$Adapter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView",
            "<*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    .prologue
    .line 116
    iget-object v1, p0, Lolive/app/OliveArtists$1;->val$tab:Landroid/app/Activity;

    iget-object v0, p0, Lolive/app/OliveArtists$1;->val$adapter:Lolive/app/OliveArtists$Adapter;

    iget-object v0, v0, Lolive/app/OliveArtists$Adapter;->artists:Ljava/util/List;

    invoke-interface {v0, p3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lolive/app/OliveArtists$Artist;

    invoke-static {v1, v0}, Lolive/app/OliveArtists;->open(Landroid/app/Activity;Lolive/app/OliveArtists$Artist;)V

    .line 117
    return-void
.end method
