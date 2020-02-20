---
layout: default
---

# [Vicky](https://github.com/devadmin-com/vicky)

## Overview

Vicky is a robot which sends JIRA activity to relevant slack channels and people. This is an overview of all the features of Vicky.
* Current Version: 2.0

### Vicky’s Life Goals

*	Improve team communication.
*	Make following processes easy and fun.
*	Ensure blocker issues are handled with due urgency!
*	Create flexible robot framework which makes it easy to add behaviours

### Related

* [Vicky 2.0 Technical Specification](https://docs.google.com/document/d/1FJ9pqHPJ3jBPb45LfxMviRjyjnPpmoSSbIfzPw4Ud10/edit#)
* [Vicky 3.0 Features spec](https://docs.google.com/document/d/1AZMyca3aNoVAB3OR_gn5sypVferJxhpazdu8BkmUWas/edit?pli=1#)
* See also: [future ideas for Vicky](https://docs.google.com/document/d/1e7Rydt8QOeFp_yx-8sr9MHZrwJzSQ-tqcLTEoKpSrU0/edit#) for some things we might want to add in the future (TBD).


## General

A slack robot which writes to different slack channels with summaries of activity information from JIRA.
[robot image courtesy of abucs@devian art](http://abucs.deviantart.com/art/Robot-couple-313055907)

### Project channel (JIRA Project -&gt; slack channel mapping)
Each JIRA project maps to one slack channel for sending notifications. 
Slack channel and jira project name have to be the same.
(convention over configuration)

#### Username mapping
Users need to have the same username on slack & jira

#### @vicky Private messages from bot to slack users
Each user has a private message channel with the bot in which they receive info messages which are relevant only to them.

#### Message Formatting
We use the following message formats sending messages to slack, see stories below on where/how this is used:

##### Simple format
*	&lt;issue type icon&gt; &lt;Number&gt; (clickable URL) &lt;Status&gt;: &lt;Summary&gt; @&lt;assignee nickname&gt;
*	&lt;commenter name&gt; ➠ &lt;latest comment&gt;

##### Assign format
*	&lt;Name user who assigned&gt; assigned to you: &lt;issue type icon&gt; &lt;Number (clickable URL)&gt; &lt;Status&gt;: &lt;Summary&gt; 
*	&lt;commenter name&gt; ➠ &lt;last comment&gt;

##### Summary format
*	&lt;issue type icon&gt; &lt;Number (clickable URL)&gt; &lt;Status&gt;: &lt;Summary&gt; @&lt;assignee nickname&gt;
*	&lt;description - first 5 lines&gt;
*	&lt;commenter name&gt; ➠ &lt;last comment&gt;

## Stories
### Legend
🤖 - indicates one listener in code.
⏰ - periodic task.
### TL-130 <issue type icon> Icons indicating JIRA issue type
We use the following icons to indicate issue type:
⚙ Type = ”Operations” issues
⚡ Type =”Urgent bug” issues
‼️ Priority=Blocker issues (any type)

### TL-129 Comment truncating
Truncate display of comments to 256 (value settable in project configuration)
### 🤖(jira events) TL-99 issue create -> project slack channel
For issues with type urgent bug or operations or priority=blocker:
On issue create send message formatted as Summary format (see Message Formatting above)
### 🤖(jira events) TL-99 issue resolve -> project slack channel
For issues with type urgent bug or operations or priority=blocke
On issue resolve send message formatted as simple format (see Message Formatting above)
### 🤖(jira events) TL-127 issue with label create, resolve -> #label slack channel
On issue create or resolve, for issues with labels send issue updates to slack channel of that name.
If a issue has a label - e.g. server-order - and there is a channel with that name that vicky is part-of (public or private) then issue updates should be sent to this channel.
send message formatted as simple format (see Message Formatting above)
### 🤖(jira events) TL-105 issue assigned -> slack private message
When an issue is assigned to a user, send them a private message.
send message formatted as assign format (see Message Formatting above)
### TL-140 don't send notification if
In all jira event listeners do not send notification if:
creator = assignee
editor = assignee
commenter = assignee
mention in comment = assignee
(same as TL-42)
### 🤖(jira events) TL-106 @reference in comment  -> slack private message
When a user is referenced in a comment send them a message on the vicky private channel as send message formatted as simple format (see Message Formatting above)
### ⏰ TL-107 priority=blocker issue not commented in 24 hours  -> slack private message
For priority=blocker issue assigned to user not commented in 24 hours   -> slack private message
Keep on re-bugging about this every 6 hours - so get message at 24, 30, 36, 42… hours after no comment.
### 🤖(jira events) TL-108 comment on issue assigned to user  -> slack private message
When an issue assigned to a user is commented, send them an update on slack private message send message formatted as simple format (see Message Formatting above)


![Vicky](https://devadmin-com.github.io/vicky/vicky-image.png)

