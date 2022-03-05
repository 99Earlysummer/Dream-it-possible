<template>
   <div id="comment-list" style="padding: 20px;">
            <div @click="returnComment" class="quick_comment"></div>
            <div  class="comment-list">
                <div>
                    <form class="new-comment">
                        <a v-if="avatar" class="avatar">
                            <img :src="avatar">
                        </a>
                        <p v-else>
                            点击<a href="javascript:void(0);" @click="login">登陆</a>后评论
                        </p>
                        <textarea placeholder="写下你的评论..." v-model="commentData.content"  contenteditable="true"></textarea>
                        <div class="write-function-block">
                            <div class="emoji-modal-wrap">
                            <el-popover
                                v-model="emojishow"
                                placement="right-start"
                                width="450"
                                trigger="click"
                                popper-class="emojicontainer emojic">
                                <ul >
                                    <li v-for="e in emojiimageList" >
                                        <span @click="select(e)"  :class="'emoji emoji_'+e"></span>
                                    </li>
                                </ul>
                                <span class="expression fl" slot="reference" @click="choose"></span>
                              </el-popover>

                            </div>
                            <a class="btn btn-send" @click="savecomment">发送</a>
                        </div>
                    </form>



                </div>
                <div id="normal-comment-list" class="normal-comment-list">
                    <div>
                        <div v-if="commentList.length==0">
                            <div class="top-title"><span>评论</span>
                                <a class="close-btn" style="display: none;">关闭评论</a>
                            </div>
                            <div class="no-comment"></div>
                            <div class="text">
                                智慧如你，不想<a @click="returnComment" style="color: orangered">发表一点想法</a>咩~
                            </div>
                        </div>
                        <div v-else>
                            <div v-for="m in commentList" class="comment">
                                <div class="author">
                                    <div class="v-tooltip-container" style="z-index: 0;">
                                        <div class="v-tooltip-content">
                                             <a href="#" target="_blank" class="avatar">
                                                <img :src="m.useravatar">
                                             </a>
                                        </div>
                                    </div>
                                    <div class="info">
                                        <a href="#" target="_blank" class="name">{{m.usernickname}}</a>
                                        <div class="meta">
                                            <span>{{m.createtime}}</span>
                                        </div>
                                    </div>
                                  </div>
                                <div class="comment-wrap">
                                    <p v-html="contentformat(m.content)"></p>
                                    <div class="tool-group">
                                    <a @click="zan" class="like-button">
                                        <span>赞</span>
                                    </a>
                                    <a @click="reply(m)" class="">
                                        <i class="iconfont ic-comment"></i>
                                        <span>回复</span>
                                    </a>
                                </div>
                              </div>
                                <div v-if="m.replylist.length>0"  class="sub-comment-list">
                                    <div v-for="s in m.replylist" class="sub-comment">
                                        <p>
                                            <div class="v-tooltip-container" style="z-index: 0;">
                                                <div class="v-tooltip-content">
                                                    <a href="#" target="_blank">{{s.usernickname}}</a>：<a href="#" class="maleskine-author" target="_blank">@{{s.tousernickname}}</a>
                                                </div> <!---->
                                           </div>
                                           <span v-html="contentformat(s.content)"></span>
                                        </p>
                                        <div class="sub-tool-group">
                                            <span>{{s.createtime}}</span>
                                            <a @click="replyParent(m,s)" class=""><i class="iconfont ic-comment"></i> <span>回复</span></a>
                                        </div>
                                        <div class="new-comment" v-show="s.isshownew">
                                            <textarea placeholder="写下你的评论..." v-model="commentData.content"  contenteditable="true"></textarea>
                                            <div class="write-function-block">
                                                <div class="emoji-modal-wrap">
                                                   <el-popover
                                                        v-model="s.emojishow"
                                                        placement="right-start"
                                                        width="450"
                                                        trigger="click"
                                                        popper-class="emojicontainer emojic">
                                                        <ul >
                                                            <li v-for="e in emojiimageList" >
                                                                <span @click="selectp(s,e)"  :class="'emoji emoji_'+e"></span>
                                                            </li>
                                                        </ul>
                                                        <span class="expression fl" slot="reference" @click="choosep(s)"></span>
                                                      </el-popover>
                                                </div>
                                                <a class="btn btn-send" @click="savecomment">发送</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="new-comment" v-show="m.isshownew">
                                    <textarea placeholder="写下你的评论..." v-model="commentData.content"  contenteditable="true"></textarea>
                                    <div class="write-function-block">
                                        <div class="emoji-modal-wrap">
                                            <el-popover
                                                v-model="m.emojishow"
                                                placement="right-start"
                                                width="450"
                                                trigger="click"
                                                popper-class="emojicontainer emojic">
                                                <ul >
                                                    <li v-for="e in emojiimageList" >
                                                        <span @click="selectp(m,e)"  :class="'emoji emoji_'+e"></span>
                                                    </li>
                                                </ul>
                                                <span class="expression fl" slot="reference" @click="choosep(m)" id="choose"></span>
                                              </el-popover>
                                        </div>
                                        <a class="btn btn-send" @click="savecomment">发送</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</template>
<script>
export default {
  name: 'Comment',
  data() {
    return {
      emojishow: false,
      emojiItems: [],
      content: '',
      contentFormatStr: '',
      emojiimageList: [],
      commentData: {
        useruuid: 0,
        touseruuid: 0,
        uuid: '',
        pid: 0,
        content: ''
      },
      commentList: [],
      avatar: '',
      showlogin: false
    }
  },
  created() {},
  mounted() {},
  methods: {}
}
</script>
<style scoped>
</style>
