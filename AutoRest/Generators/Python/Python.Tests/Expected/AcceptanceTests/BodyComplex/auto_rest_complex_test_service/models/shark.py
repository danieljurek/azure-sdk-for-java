# coding=utf-8
# --------------------------------------------------------------------------
# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License. See License.txt in the project root for
# license information.
#
# Code generated by Microsoft (R) AutoRest Code Generator 0.14.0.0
# Changes may cause incorrect behavior and will be lost if the code is
# regenerated.
# --------------------------------------------------------------------------

from .fish import Fish


class Shark(Fish):

    _required = ['birthday']

    _attribute_map = {
        'age': {'key': 'age', 'type': 'int'},
        'birthday': {'key': 'birthday', 'type': 'iso-8601'},
    }

    _subtype_map = {
        'fishtype': {'sawshark': 'Sawshark', 'goblin': 'Goblinshark', 'cookiecuttershark': 'Cookiecuttershark'}
    }

    def __init__(self, *args, **kwargs):

        self.age = None
        self.birthday = None

        super(Shark, self).__init__(*args, **kwargs)

        self.fishtype = 'shark'
