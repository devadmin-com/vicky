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
### Project channel (JIRA Project -> slack channel mapping)
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
*	<issue type icon> <Number> (clickable URL) <Status>: <Summary> @<assignee nickname>
*	<commenter name> ➠ <latest comment>
##### Assign format
*	<Name user who assigned> assigned to you: <issue type icon> <Number (clickable URL)> <Status>: <Summary> 
*	<commenter name> ➠ <last comment>
##### Summary format
*	<issue type icon> <Number (clickable URL)> <Status>: <Summary> @<assignee nickname>
*	<description - first 5 lines>
*	<commenter name> ➠ <last comment>

